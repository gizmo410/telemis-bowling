/** Create DML and DDL ROLES */
DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_group
      WHERE groname = 'midas_dml'
  )
  THEN
    EXECUTE 'CREATE ROLE midas_dml;';
  END IF;
END
$$;

DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_group
      WHERE groname = 'midas_ddl'
  )
  THEN
    EXECUTE 'CREATE ROLE midas_ddl;';
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
      WHERE rolname = 'midas'
  )
  THEN
    EXECUTE 'CREATE ROLE midas LOGIN ENCRYPTED PASSWORD ''md5f8fb42a45c61d0ae7af1fa3a5e4b94c0'' VALID UNTIL ''infinity'';';
  END IF;
END
$$;

DO $$
BEGIN
  IF NOT EXISTS(
      SELECT
        *
      FROM pg_catalog.pg_roles
      WHERE rolname = 'midas_flyway'
  )
  THEN
    EXECUTE 'CREATE ROLE midas_flyway LOGIN ENCRYPTED PASSWORD ''Midas_flywaY'' VALID UNTIL ''infinity'';';
  END IF;
END
$$;

/* Setting privileges for dml and ddl, while revoking all from public. */
DO $$
BEGIN
  EXECUTE 'GRANT CONNECT ON DATABASE ' || current_database() || ' TO midas_ddl, midas_dml;';
  EXECUTE 'GRANT ALL ON DATABASE ' || current_database() || ' TO midas_ddl;';
  EXECUTE 'REVOKE ALL ON DATABASE ' || current_database() || ' FROM public;';
END;
$$;

/* Grant our application user and flyway user the correct role and privileges to get started */
GRANT midas_dml TO midas;
GRANT midas_ddl TO midas_flyway;
REVOKE ALL ON SCHEMA PUBLIC FROM PUBLIC;
GRANT ALL ON SCHEMA public TO midas_ddl;
GRANT USAGE ON SCHEMA public TO midas_dml;

/* Grant our application user and flyway user the correct role and privileges to get started */
/*GRANT ALL ON DATABASE midas TO midas_ddl;
GRANT ALL ON SCHEMA midas TO midas_ddl;*/