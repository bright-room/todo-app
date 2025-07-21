-- スキーマ管理
drop schema if exists todo_app cascade;
create schema todo_app;
set search_path to todo_app;

-- タスクテーブル
create table tasks (
    id uuid primary key default gen_random_uuid(),
    title varchar(200) not null, -- タスクタイトル
    description text not null default '', -- タスク説明
    due_date date not null, -- 期日
    is_completed boolean not null default false, -- 完了フラグ
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_tasks_created_at on tasks(created_at);
create index idx_tasks_due_date on tasks(due_date);
create index idx_tasks_is_completed on tasks(is_completed);

-- コメント追加
comment on table tasks is 'タスク';
comment on column tasks.id is 'タスクID';
comment on column tasks.title is 'タスクタイトル';
comment on column tasks.description is 'タスク説明';
comment on column tasks.due_date is '期日';
comment on column tasks.is_completed is '完了フラグ';
comment on column tasks.created_at is '作成日時';