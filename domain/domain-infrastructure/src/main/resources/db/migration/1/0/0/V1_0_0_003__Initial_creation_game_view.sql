CREATE TABLE bowling.game_view (
  identifier          VARCHAR(255) NOT NULL,
  status              VARCHAR(50) NOT NULL,
  players             JSON NOT NULL,
  current_player_name VARCHAR(255) NOT NULL,
  version             INT4 NOT NULL DEFAULT 0,
  CONSTRAINT PK_GameView PRIMARY KEY (identifier)
);

ALTER TABLE bowling.game_view OWNER TO bowling_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON bowling.game_view TO bowling_dml, bowling;
REVOKE ALL ON bowling.game_view FROM PUBLIC;
