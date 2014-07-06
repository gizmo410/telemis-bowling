DROP SEQUENCE IF EXISTS public.hibernate_sequence CASCADE;

CREATE SCHEMA IF NOT EXISTS midas;
CREATE SCHEMA IF NOT EXISTS midas_deployment;
CREATE SCHEMA IF NOT EXISTS midas_view;

GRANT USAGE ON SCHEMA midas_view TO midas_dml;
GRANT ALL ON SCHEMA midas_view TO midas_ddl;
ALTER SCHEMA midas_view
OWNER TO midas_ddl;
REVOKE ALL ON SCHEMA midas_view FROM PUBLIC;

GRANT USAGE ON SCHEMA midas TO midas_dml;
GRANT ALL ON SCHEMA midas TO midas_ddl;
ALTER SCHEMA midas
OWNER TO midas_ddl;
REVOKE ALL ON SCHEMA midas FROM PUBLIC;