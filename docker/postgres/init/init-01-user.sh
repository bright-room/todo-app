#!/usr/bin/env bash

set -e

psql -v ON_ERROR_STOP=1 --username "${POSTGRES_USER}" --dbname "${POSTGRES_DB}" <<-EOSQL
  -- root user
  drop role if exists root;
  create role
    root
  with
    login
    password '${POSTGRES_ROOT_PASSWORD}';

  -- admin user
  drop role if exists ${POSTGRES_ADMIN_USERNAME};
  create role
    ${POSTGRES_ADMIN_USERNAME}
  with
    superuser
    createdb
    createrole
    noinherit
    login
    noreplication
    bypassrls
    connection limit 1
    password'${POSTGRES_ADMIN_PASSWORD}';

  -- operation user
  drop role if exists ${POSTGRES_OPERATIONS_USERNAME};
  create role
    ${POSTGRES_OPERATIONS_USERNAME}
  with
    nosuperuser
    nocreatedb
    nocreaterole
    noinherit
    login
    noreplication
    bypassrls
    password '${POSTGRES_OPERATIONS_PASSWORD}';
EOSQL
