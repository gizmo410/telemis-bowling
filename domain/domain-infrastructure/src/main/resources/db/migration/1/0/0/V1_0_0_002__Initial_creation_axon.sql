CREATE SEQUENCE bowling.hibernate_sequence;

/* Axon 2.1 */
CREATE TABLE bowling.AssociationValueEntry (
  id               INT8 NOT NULL,
  associationKey   VARCHAR(255),
  associationValue VARCHAR(255),
  sagaId           VARCHAR(255),
  sagaType         VARCHAR(255),
  CONSTRAINT PK_AssociationValueEntry PRIMARY KEY (id)
);

CREATE TABLE bowling.DomainEventEntry (
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
ALTER TABLE bowling.DomainEventEntry ADD CONSTRAINT UC_DomainEventEntry_eventId UNIQUE (eventIdentifier);
CREATE TABLE bowling.SagaEntry (
  sagaId         VARCHAR(255) NOT NULL,
  revision       VARCHAR(255),
  sagaType       VARCHAR(255),
  serializedSaga OID,
  CONSTRAINT PK_SagaEntry PRIMARY KEY (sagaId)
);
CREATE TABLE bowling.SnapshotEventEntry (
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
ALTER TABLE bowling.SnapshotEventEntry ADD CONSTRAINT UC_SnapshotEventEntry_eventId UNIQUE (eventIdentifier);

/* Axon 2.1 */
ALTER SEQUENCE bowling.hibernate_sequence
OWNER TO bowling_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON bowling.hibernate_sequence TO bowling_dml, bowling;
REVOKE ALL ON bowling.hibernate_sequence FROM PUBLIC;

ALTER TABLE bowling.AssociationValueEntry OWNER TO bowling_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON bowling.AssociationValueEntry TO bowling_dml, bowling;
REVOKE ALL ON bowling.AssociationValueEntry FROM PUBLIC;

ALTER TABLE bowling.DomainEventEntry OWNER TO bowling_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON bowling.DomainEventEntry TO bowling_dml, bowling;
REVOKE ALL ON bowling.DomainEventEntry FROM PUBLIC;

ALTER TABLE bowling.SagaEntry OWNER TO bowling_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON bowling.SagaEntry TO bowling_dml, bowling;
REVOKE ALL ON bowling.SagaEntry FROM PUBLIC;

ALTER TABLE bowling.SnapshotEventEntry OWNER TO bowling_ddl;
GRANT SELECT, UPDATE, INSERT, DELETE ON bowling.SnapshotEventEntry TO bowling_dml, bowling;
REVOKE ALL ON bowling.SnapshotEventEntry FROM PUBLIC;
