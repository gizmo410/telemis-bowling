/** Create DML and DDL ROLES */
DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_group
      WHERE groname = 'bowling_dml'
  )
  THEN
    EXECUTE 'CREATE ROLE bowling_dml;';
  END IF;
END
$$;

DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_group
      WHERE groname = 'bowling_ddl'
  )
  THEN
    EXECUTE 'CREATE ROLE bowling_ddl;';
  END IF;
END
$$;

/** Create application user and flyway application user with default passwords */
DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_roles
      WHERE rolname = 'bowling'
  )
  THEN
    EXECUTE 'CREATE ROLE bowling LOGIN ENCRYPTED PASSWORD ''BowlinG'' VALID UNTIL ''infinity'';';
  END IF;
END
$$;

DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_roles
      WHERE rolname = 'bowling_flyway'
  )
  THEN
    EXECUTE 'CREATE ROLE bowling_flyway LOGIN ENCRYPTED PASSWORD ''Bowling_flywaY'' VALID UNTIL ''infinity'';';
  END IF;
END
$$;

/* Setting privileges for dml and ddl, while revoking all from public. */
DO $$
BEGIN
  EXECUTE 'GRANT CONNECT ON DATABASE ' || current_database() || ' TO bowling_ddl, bowling_dml;';
  EXECUTE 'GRANT ALL ON DATABASE ' || current_database() || ' TO bowling_ddl;';
  EXECUTE 'REVOKE ALL ON DATABASE ' || current_database() || ' FROM public;';
END;
$$;

/* Grant our application user and flyway user the correct role and privileges to get started */
GRANT bowling_dml TO bowling;
GRANT bowling_ddl TO bowling_flyway;
REVOKE ALL ON SCHEMA PUBLIC FROM PUBLIC;
GRANT ALL ON SCHEMA public TO bowling_ddl;
GRANT USAGE ON SCHEMA public TO bowling_dml;

/* Grant our application user and flyway user the correct role and privileges to get started */
/*GRANT ALL ON DATABASE bowling TO bowling_ddl;
GRANT ALL ON SCHEMA bowling TO bowling_ddl;*/