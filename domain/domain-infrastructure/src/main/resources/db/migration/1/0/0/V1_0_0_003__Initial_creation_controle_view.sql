
CREATE TABLE midas_view.controle_view (
  unieke_sleutel            VARCHAR(255) NOT NULL,
  uitvoerend_toezichthouder VARCHAR(255) NOT NULL,
  kenmerk                   VARCHAR(255),
  onderwerp                 VARCHAR(255),
  omschrijving              VARCHAR(1000),
  besluit                   VARCHAR(255),
  alleen_besluit            BOOLEAN,
  status                    VARCHAR(50),
  programma_onderdelen      JSON NOT NULL,
  intern_begeleider         JSON,
  CONSTRAINT PK_ControleView PRIMARY KEY (unieke_sleutel)
);

ALTER TABLE midas_view.controle_view OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas_view.controle_view TO midas_dml, midas;
REVOKE ALL ON midas_view.controle_view FROM PUBLIC;