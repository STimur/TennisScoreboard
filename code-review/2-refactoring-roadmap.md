# Роадмап рефакторинга по файлам

Это упорядоченный список файлов, которые следует исправлять в соответствии с замечаниями в комментариях. Рекомендую двигаться последовательно.

Файлы, не указанные в списке, можно исправлять в любом порядке.

### Шаг 1: Entity и слой доступа к данным

- `/entity/Player.java`
- `/entity/Match.java`
- `/dao/PlayerDao.java`
- `/dao/MatchDao.java`

### Шаг 2: Доменные модели

- `/domain/*`

### Шаг 3: Сервисный слой

- `/service/PlayerService.java`
- `/service/OngoingMatchService.java`
- `/service/MatchService.java`

### Шаг 4: DTO (Data Transfer Object)

- `/dto/PlayerScoreDto.java`
- `/dto/CreateMatchRequest.java`

### Шаг 5: Контроллер

- `/controller/HelloController.java`
- `/controller/PlayerController.java`

### Шаг 6: Конфигурация, мапперы, валидаторы, обработка исключений

- `/config/WebConfig.java`
- `/config/DataSourceConfig.java`
- `/config/HibernateConfig.java`
- `/config/FlywayConfig.java`
- `/controller/GlobalExceptionHandler.java`
- `/infrastructure/TransactionAction.java`
- `/infrastructure/TransactionRunner.java`
- `/exception/DataAccessException.java`

### Шаг 7: Тесты

- `/test/java/org/timur/roadmap/tennisscoreboard/domain/TieBreakScoreTest.java`
- `/test/java/org/timur/roadmap/tennisscoreboard/domain/OrdinaryGameScoreTest.java`
- `/test/java/org/timur/roadmap/tennisscoreboard/domain/SetScoreTest.java`
- `/test/java/org/timur/roadmap/tennisscoreboard/domain/MatchScoreTest.java`
