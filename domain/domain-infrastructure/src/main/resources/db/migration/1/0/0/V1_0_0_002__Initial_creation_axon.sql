CREATE SEQUENCE midas.hibernate_sequence;

/* Axon 2.1 */
CREATE TABLE midas.AssociationValueEntry (
  id               INT8 NOT NULL,
  associationKey   VARCHAR(255),
  associationValue VARCHAR(255),
  sagaId           VARCHAR(255),
  sagaType         VARCHAR(255),
  CONSTRAINT PK_AssociationValueEntry PRIMARY KEY (id)
);

CREATE TABLE midas.DomainEventEntry (
  aggregateIdentifier VARCHAR(255) NOT NULL,
  sequenceNumber      INT8         NOT NULL,
  type                VARCHAR(255) NOT NULL,
  eventIdentifier     VARCHAR(255) NOT NULL,
  metaData            OID,
  payload             OID          NOT NULL,
  payloadRevision     VARCHAR(255),
  payloadType         VARCHAR(255) NOT NULL,
  timeStamp           VARCHAR(255) NOT NULL,
  CONSTRAINT PK_DomainEventEntry PRIMARY KEY (aggregateIdentifier, sequenceNumber, type)
);
ALTER TABLE midas.DomainEventEntry ADD CONSTRAINT UC_DomainEventEntry_eventId UNIQUE (eventIdentifier);
CREATE TABLE midas.SagaEntry (
  sagaId         VARCHAR(255) NOT NULL,
  revision       VARCHAR(255),
  sagaType       VARCHAR(255),
  serializedSaga OID,
  CONSTRAINT PK_SagaEntry PRIMARY KEY (sagaId)
);
CREATE TABLE midas.SnapshotEventEntry (
  aggregateIdentifier VARCHAR(255) NOT NULL,
  sequenceNumber      INT8         NOT NULL,
  type                VARCHAR(255) NOT NULL,
  eventIdentifier     VARCHAR(255) NOT NULL,
  metaData            OID,
  payload             OID          NOT NULL,
  payloadRevision     VARCHAR(255),
  payloadType         VARCHAR(255) NOT NULL,
  timeStamp           VARCHAR(255) NOT NULL,
  CONSTRAINT PK_SnapshotEventEntry PRIMARY KEY (aggregateIdentifier, sequenceNumber, type)
);
ALTER TABLE midas.SnapshotEventEntry ADD CONSTRAINT UC_SnapshotEventEntry_eventId UNIQUE (eventIdentifier);

/* Axon 2.1 */
ALTER SEQUENCE midas.hibernate_sequence
OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas.hibernate_sequence TO midas_dml, midas;
REVOKE ALL ON midas.hibernate_sequence FROM PUBLIC;

ALTER TABLE midas.AssociationValueEntry OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas.AssociationValueEntry TO midas_dml, midas;
REVOKE ALL ON midas.AssociationValueEntry FROM PUBLIC;

ALTER TABLE midas.DomainEventEntry OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas.DomainEventEntry TO midas_dml, midas;
REVOKE ALL ON midas.DomainEventEntry FROM PUBLIC;

ALTER TABLE midas.SagaEntry OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas.SagaEntry TO midas_dml, midas;
REVOKE ALL ON midas.SagaEntry FROM PUBLIC;

ALTER TABLE midas.SnapshotEventEntry OWNER TO midas_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON midas.SnapshotEventEntry TO midas_dml, midas;
REVOKE ALL ON midas.SnapshotEventEntry FROM PUBLIC;
