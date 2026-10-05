package org.timur.roadmap.tennisscoreboard.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchRequest;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchResponse;
import org.timur.roadmap.tennisscoreboard.dto.FinishedMatchesResponse;
import org.timur.roadmap.tennisscoreboard.dto.MatchDto;
import org.timur.roadmap.tennisscoreboard.dao.MatchDao;
import org.timur.roadmap.tennisscoreboard.dto.PageResult;
import org.timur.roadmap.tennisscoreboard.dto.PointRequest;
import org.timur.roadmap.tennisscoreboard.dto.ScoreResponse;
import org.timur.roadmap.tennisscoreboard.entity.Match;
import org.timur.roadmap.tennisscoreboard.entity.Player;
import org.timur.roadmap.tennisscoreboard.infrastructure.TransactionRunner;
import org.timur.roadmap.tennisscoreboard.mapper.MatchMapper;
import org.timur.roadmap.tennisscoreboard.exception.MatchNotFoundException;
import org.timur.roadmap.tennisscoreboard.mapper.OngoingMatchMapper;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    // Нет интерфейса для этого класса. (см. файл "service.md" в этом же пакете)

    // TODO: Класс совмещает несколько разных ответственностей:
        // - работает с текущими матчами
        // - работает с завершёнными матчами
        // - занимается валидацией данных из запроса
        // - оркестрирует преобразование List<Entity> в List<DTO>
        //
        // Это нарушает принцип единой ответственности (SRP).
        // Стоит разделить логику работы с разными матчами на разные более специализированные классы.
        // Логику валидации запроса оставить только в сервлете,
        // а преобразование List<Entity> —> List<DTO> перенести в маппер.

    private final MatchDao matchDao;
    private final MatchMapper matchMapper;
    private final OngoingMatchMapper ongoingMatchMapper;
    private final PlayerService playerService;
    private final OngoingMatchService ongoingMatchService;
    private final TransactionRunner txRunner;

    public MatchService(MatchDao matchDao, MatchMapper matchMapper,
                        OngoingMatchMapper ongoingMatchMapper, PlayerService playerService,
                        OngoingMatchService ongoingMatchService, TransactionRunner txRunner) {
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
        this.ongoingMatchMapper = ongoingMatchMapper;
        this.playerService = playerService;
        this.ongoingMatchService = ongoingMatchService;
        this.txRunner = txRunner;
    }

    // Метод нигде не используется в проекте. Такой код стоит удалять перед коммитом.
    public List<MatchDto> getAllMatches() {
        return txRunner.runInTransaction(
                () -> matchDao.findAll()
                        .stream()
                        .map(matchMapper::toDto)
                        .toList()
        );
    }

    public CreateMatchResponse createMatch(CreateMatchRequest request) {
        return txRunner.runInTransaction(() -> {
            String firstPlayerName = playerService.findOrCreate(request.firstPlayerName()).getName();
            String secondPlayerName = playerService.findOrCreate(request.secondPlayerName()).getName();

            UUID id = UUID.randomUUID();

            OngoingMatch match = new OngoingMatch(
                    id,
                    firstPlayerName,
                    secondPlayerName
            );

            ongoingMatchService.add(match);

            return new CreateMatchResponse(id);
        });
    }

    // Аннотация валидации PointRequest уже есть в контроллере и там её правильное место.
        // Запрос от пользователя стоит проверять как можно ближе ко входу этих данных в приложение.
        // Здесь аннотацию @Valid стоит удалить, так класс станет строже соблюдать принцип единой ответственности (SRP).
    public ScoreResponse addPoint(UUID id, @Valid PointRequest request) {
        OngoingMatch ongoingMatch = ongoingMatchService.find(id)
                .orElseThrow(MatchNotFoundException::new);

        // TODO: Race condition при обработке выигранного очка.
            // Например, если пользователь очень быстро нажмёт кнопку выигрыша очка, браузер отправит два POST-запроса почти одновременно.
            // Эти два запроса будут обработаны в двух разных потоках и оба потока будут работать с одним и тем же общим объектом `OngoingMatch`,
            // что может привести к попытке начисления очка в уже завершённом матче:
                // Оба потока получают ссылку на объект матча — ongoingMatchService.find().
                // Первый поток входит в synchronized, добавляет решающее очко, isFinished() становится true,
                // он сохраняет матч в БД и удаляет его из хранилища.
                // Затем второй поток, который держит уже устаревшую к этому моменту ссылку,
                // входит в synchronized и вызывает addPoint() на уже завершённом матче.
                // Дальше ongoingMatch.isFinished() для второго потока снова вернёт true
                // и через saveFinishedMatch в БД появится дубль матча.
            // Чтобы это исправить, например нужно внутри блока synchronized выполнять логику только если матч не завершён.
        synchronized (ongoingMatch) {
            ongoingMatch.addPoint(request.name());

            if (ongoingMatch.isFinished()) {
                saveFinishedMatch(ongoingMatch);
                ongoingMatchService.remove(id);
            }

            return ongoingMatchMapper.toDto(ongoingMatch);
        }
    }

    public ScoreResponse getScore(UUID uuid) {
        OngoingMatch match = ongoingMatchService.find(uuid)
                .orElseThrow(MatchNotFoundException::new);

        return ongoingMatchMapper.toDto(match);
    }

    public FinishedMatchesResponse getFinishedMatches(int page, String playerName) {
        return txRunner.runInTransaction(() -> {
            PageResult<Match> matches = matchDao.findFinishedMatches(page - 1, playerName);

            return new FinishedMatchesResponse(

                    // Логику преобразования List<Entity> —> List<DTO> стоит перенести в маппер.
                        // Так класс станет строже соблюдать принцип единой ответственности (SRP).
                    matches.items()
                            .stream()
                            .map(matchMapper::toFinishedDto)
                            .toList(),
                    page,
                    matches.totalPages()
            );
        });
    }

    private void saveFinishedMatch(OngoingMatch ongoingMatch) {
        txRunner.runInTransaction(() -> {
            Player firstPlayer = playerService.findOrCreate(ongoingMatch.getFirstPlayerName());
            Player secondPlayer = playerService.findOrCreate(ongoingMatch.getSecondPlayerName());

            Player winner = ongoingMatch.getWinnerName().equals(firstPlayer.getName()) ? firstPlayer : secondPlayer;

            Match finishedMatch = new Match(firstPlayer, secondPlayer, winner);

            matchDao.save(finishedMatch);
        });
    }
}