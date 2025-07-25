-- スキーマ管理
drop schema if exists todo_app cascade;
create schema todo_app;
set search_path to todo_app;

-- タスクテーブル
create table tasks (
    id uuid primary key default gen_random_uuid(),
    title varchar(100) not null, -- タスクタイトル
    description text not null default '', -- タスク説明
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_tasks_created_at on tasks(created_at);

comment on table tasks is 'タスク';
comment on column tasks.id is 'タスクID';
comment on column tasks.title is 'タスクタイトル';
comment on column tasks.description is 'タスク説明';
comment on column tasks.created_at is '作成日時';

-- タスク期限テーブル
create table task_due_dates (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references tasks(id), -- タスクID
    due_date date not null, -- 期限日
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_due_dates_created_at on task_due_dates(created_at);

comment on table task_due_dates is 'タスク期限';
comment on column task_due_dates.id is 'タスクID';
comment on column task_due_dates.task_id is 'タスクID';
comment on column task_due_dates.due_date is '期限日';
comment on column task_due_dates.created_at is '作成日時';
