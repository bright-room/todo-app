#!/usr/bin/env bash

set -e

export POSTGRES_PASSWORD=${POSTGRES_ADMIN_PASSWORD}
psql -v ON_ERROR_STOP=1 --username "${POSTGRES_ADMIN_USERNAME}" --dbname "$POSTGRES_DB" <<-EOSQL
  drop database if exists ${POSTGRES_PRODUCTION_DBNAME};
  create database ${POSTGRES_PRODUCTION_DBNAME} with owner ${POSTGRES_ADMIN_USERNAME};

  \c todo

  -- 1) データベース接続権限
  grant connect on database ${POSTGRES_PRODUCTION_DBNAME} to ${POSTGRES_OPERATIONS_USERNAME};

  -- 2) public スキーマの既定権限を厳格化(暗黙の権限を剥奪)
  revoke all on schema public from public;
  grant usage, create on schema public to ${POSTGRES_ADMIN_USERNAME};
  grant usage on schema public to ${POSTGRES_OPERATIONS_USERNAME};

  -- 3) 既存テーブル・シーケンスに対する権限付与
  grant select, insert, update, delete on all tables in schema public to ${POSTGRES_OPERATIONS_USERNAME};
  grant usage, select, update on all sequences in schema public to ${POSTGRES_OPERATIONS_USERNAME};

  -- 4) 将来作成されるオブジェクトのデフォルト権限（bradmin が作成するとき）
  alter default privileges for role ${POSTGRES_ADMIN_USERNAME} in schema public
    grant select, insert, update, delete on tables to ${POSTGRES_OPERATIONS_USERNAME};
  alter default privileges for role ${POSTGRES_ADMIN_USERNAME} in schema public
    grant usage, select, update on sequences to ${POSTGRES_OPERATIONS_USERNAME};
EOSQL
