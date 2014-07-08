DROP SEQUENCE IF EXISTS public.hibernate_sequence CASCADE;

CREATE SCHEMA IF NOT EXISTS bowling;

GRANT USAGE ON SCHEMA bowling TO bowling_dml;
GRANT ALL ON SCHEMA bowling TO bowling_ddl;
ALTER SCHEMA bowling
OWNER TO bowling_ddl;
REVOKE ALL ON SCHEMA bowling FROM PUBLIC;