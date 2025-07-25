drop schema if exists todo_app cascade;
create schema todo_app;
set search_path to todo_app;

-- タスクを一意に表す識別子
create table task_id (
    id uuid primary key default gen_random_uuid(),
    created_at timestamp not null default current_timestamp
);
create index idx_task_identity_created_at on task_id(created_at);

comment on table task_id is 'タスクを一意に表す識別子';
comment on column task_id.id is '識別子';
comment on column task_id.created_at is '作成日時';

-- タスク内容
create table task_content (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references task_id(id),
    title varchar(200) not null,
    description text not null default '',
    created_at timestamp not null default current_timestamp
);
create index idx_task_content_created_at on task_content(created_at);

comment on table task_content is 'タスク内容';
comment on column task_content.id is 'タスク内容ID';
comment on column task_content.task_id is 'タスクID';
comment on column task_content.title is 'タスクタイトル';
comment on column task_content.description is 'タスク説明';
comment on column task_content.created_at is '作成日時';

-- タスクステータス
create type task_status_type as enum ('未完了', '完了');
create table task_status (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references task_id(id),
    status task_status_type not null,
    created_at timestamp not null default current_timestamp
);
create index idx_task_status_created_at on task_status(created_at);

comment on table task_status is 'タスクステータス';
comment on column task_status.id is 'タスクステータスID';
comment on column task_status.task_id is 'タスクID';
comment on column task_status.status is 'ステータス';
comment on column task_status.created_at is '作成日時';

-- タスク完了日時
create table task_complete_time (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references task_id(id),
    completed_at timestamp not null default current_timestamp
);
create index idx_task_complete_time_completed_at on task_complete_time(completed_at);

comment on table task_complete_time is 'タスク完了日時';
comment on column task_complete_time.id is 'タスク完了日時ID';
comment on column task_complete_time.task_id is 'タスクID';
comment on column task_complete_time.completed_at is '完了日時';
