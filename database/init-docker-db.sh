#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER docker WITH PASSWORD 'docker';
	CREATE DATABASE cafeteria;
	GRANT ALL PRIVILEGES ON DATABASE cafeteria TO docker;
EOSQL