
CREATE TABLE midas_view.medewerker_view (
  unieke_sleutel  VARCHAR(255) NOT NULL,
  voornaam        VARCHAR(255) NOT NULL,
  achternaam      VARCHAR(255) NOT NULL,
  full_text       TEXT,
  CONSTRAINT PK_MedewerkerView PRIMARY KEY (unieke_sleutel)
);

ALTER TABLE midas_view.medewerker_view OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas_view.medewerker_view TO midas_dml, midas;
REVOKE ALL ON midas_view.medewerker_view FROM PUBLIC;