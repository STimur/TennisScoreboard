CREATE TABLE players
(
    id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE matches
(
    id      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    player1 INT NOT NULL,
    player2 INT NOT NULL,
    winner  INT NOT NULL,

    CONSTRAINT fk_matches_player1
        FOREIGN KEY (player1)
            REFERENCES players (id),

    CONSTRAINT fk_matches_player2
        FOREIGN KEY (player2)
            REFERENCES players (id),

    CONSTRAINT fk_matches_winner
        FOREIGN KEY (winner)
            REFERENCES players (id),

    CONSTRAINT chk_players_different
        CHECK (player1 <> player2),

    CONSTRAINT chk_winner_is_player
        CHECK (winner = player1 OR winner = player2)
);