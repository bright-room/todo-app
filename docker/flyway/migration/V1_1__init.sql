-- TODOアプリ用データベーススキーマ
-- 作成日: 初期マイグレーション

-- スキーマ管理
drop schema if exists todo_app cascade;
create schema todo_app;
set search_path to todo_app;

-- 繰り返しルール列挙型
create type repeat_rule_type as enum (
    '繰り返しなし',
    '毎日',
    '平日のみ',
    '休日のみ',
    '週単位',
    '月単位',
    '年単位'
);

-- タスクテーブル
create table tasks (
    id uuid primary key default gen_random_uuid(),
    title varchar(200) not null, -- タスクタイトル
    description text not null default '', -- タスク説明
    due_date date, -- 期限日
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_tasks_created_at on tasks(created_at);

comment on table tasks is 'タスク';
comment on column tasks.id is 'タスクID';
comment on column tasks.title is 'タスクタイトル';
comment on column tasks.description is 'タスク説明';
comment on column tasks.due_date is '期限日';
comment on column tasks.created_at is '作成日時';

-- サブタスクテーブル
create table subtasks (
    id uuid primary key default gen_random_uuid(),
    parent_task_id uuid not null references tasks(id), -- 親タスクID
    title varchar(200) not null, -- サブタスクタイトル
    description text not null default '', -- サブタスク説明
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_subtasks_parent_task_id on subtasks(parent_task_id);
create index idx_subtasks_created_at on subtasks(created_at);

comment on table subtasks is 'サブタスク';
comment on column subtasks.id is 'サブタスクID';
comment on column subtasks.parent_task_id is '親タスクID';
comment on column subtasks.title is 'サブタスクタイトル';
comment on column subtasks.description is 'サブタスク説明';
comment on column subtasks.created_at is '作成日時';

-- タスク繰り返しルールテーブル
create table task_repeat_rules (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references tasks(id), -- タスクID
    repeat_type repeat_rule_type not null, -- 繰り返しタイプ
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_repeat_rules_task_id on task_repeat_rules(task_id);

comment on table task_repeat_rules is 'タスク繰り返しルール';
comment on column task_repeat_rules.id is 'タスク繰り返しルールID';
comment on column task_repeat_rules.task_id is 'タスクID';
comment on column task_repeat_rules.repeat_type is '繰り返しタイプ';
comment on column task_repeat_rules.created_at is '作成日時';

-- 週単位繰り返し設定テーブル
create table task_weekly_repeat_configs (
    id uuid primary key default gen_random_uuid(),
    repeat_rule_id uuid not null references task_repeat_rules(id), -- 繰り返しルールID
    day_of_week varchar(10) not null, -- 曜日
    created_at timestamp not null default current_timestamp, -- 作成日時

    unique (repeat_rule_id, day_of_week)
);

-- インデックス作成
create index idx_task_weekly_repeat_configs_repeat_rule_id on task_weekly_repeat_configs(repeat_rule_id);

comment on table task_weekly_repeat_configs is '週単位繰り返し設定';
comment on column task_weekly_repeat_configs.id is '週単位繰り返し設定ID';
comment on column task_weekly_repeat_configs.repeat_rule_id is '繰り返しルールID';
comment on column task_weekly_repeat_configs.day_of_week is '曜日';
comment on column task_weekly_repeat_configs.created_at is '作成日時';

-- 月単位繰り返し設定テーブル
create table task_monthly_repeat_configs (
    id uuid primary key default gen_random_uuid(),
    repeat_rule_id uuid not null references task_repeat_rules(id), -- 繰り返しルールID
    day_of_month integer not null check (day_of_month >= 1 and day_of_month <= 31), -- 月の日
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_monthly_repeat_configs_repeat_rule_id on task_monthly_repeat_configs(repeat_rule_id);

comment on table task_monthly_repeat_configs is '月単位繰り返し設定';
comment on column task_monthly_repeat_configs.id is '月単位繰り返し設定ID';
comment on column task_monthly_repeat_configs.repeat_rule_id is '繰り返しルールID';
comment on column task_monthly_repeat_configs.day_of_month is '月の日';
comment on column task_monthly_repeat_configs.created_at is '作成日時';

-- 年単位繰り返し設定テーブル
create table task_yearly_repeat_configs (
    id uuid primary key default gen_random_uuid(),
    repeat_rule_id uuid not null references task_repeat_rules(id), -- 繰り返しルールID
    month_of_year integer not null check (month_of_year >= 1 and month_of_year <= 12), -- 年の月
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_yearly_repeat_configs_repeat_rule_id on task_yearly_repeat_configs(repeat_rule_id);

comment on table task_yearly_repeat_configs is '年単位繰り返し設定';
comment on column task_yearly_repeat_configs.id is '年単位繰り返し設定ID';
comment on column task_yearly_repeat_configs.repeat_rule_id is '繰り返しルールID';
comment on column task_yearly_repeat_configs.month_of_year is '年の月';
comment on column task_yearly_repeat_configs.created_at is '作成日時';

-- タスクリマインダーテーブル
create table task_reminders (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references tasks(id), -- タスクID
    reminder_datetime timestamp not null -- リマインダー日時
);

-- インデックス作成
create index idx_task_reminders_task_id on task_reminders(task_id);

comment on table task_reminders is 'タスクリマインダー';
comment on column task_reminders.id is 'タスクリマインダーID';
comment on column task_reminders.task_id is 'タスクID';
comment on column task_reminders.reminder_datetime is 'リマインダー日時';

-- タスクグループテーブル
create table task_groups (
    id uuid primary key default gen_random_uuid(),
    title varchar(100) not null, -- タスクグループタイトル
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_groups_created_at on task_groups(created_at);

comment on table task_groups is 'タスクグループ';
comment on column task_groups.id is 'タスクグループID';
comment on column task_groups.title is 'タスクグループタイトル';
comment on column task_groups.created_at is '作成日時';

-- タグテーブル
create table tags (
    id uuid primary key default gen_random_uuid(),
    title varchar(30) not null, -- タグタイトル
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_tags_created_at on tags(created_at);

comment on table tags is 'タグ';
comment on column tags.id is 'タグID';
comment on column tags.title is 'タグタイトル';
comment on column tags.created_at is '作成日時';

-- タスク-タグ関連テーブル
create table task_tags (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references tasks(id), -- タスクID
    tag_id uuid not null references tags(id), -- タグID
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_tags_task_id on task_tags(task_id);
create index idx_task_tags_tag_id on task_tags(tag_id);

comment on table task_tags is 'タスク-タグ関連';
comment on column task_tags.id is 'タスク-タグ関連ID';
comment on column task_tags.task_id is 'タスクID';
comment on column task_tags.tag_id is 'タグID';
comment on column task_tags.created_at is '作成日時';

-- タスクグループ-タスク関連テーブル
create table task_group_tasks (
    id uuid primary key default gen_random_uuid(),
    task_group_id uuid not null references task_groups(id), -- タスクグループID
    task_id uuid not null references tasks(id), -- タスクID
    created_at timestamp not null default current_timestamp -- 作成日時
);

-- インデックス作成
create index idx_task_group_tasks_task_group_id on task_group_tasks(task_group_id);
create index idx_task_group_tasks_task_id on task_group_tasks(task_id);

comment on table task_group_tasks is 'タスクグループ-タスク関連';
comment on column task_group_tasks.id is 'タスクグループ-タスク関連ID';
comment on column task_group_tasks.task_group_id is 'タスクグループID';
comment on column task_group_tasks.task_id is 'タスクID';
comment on column task_group_tasks.created_at is '作成日時';

-- タスク完了状態テーブル
create table task_completions (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null references tasks(id), -- タスクID
    completed_at timestamp not null default current_timestamp -- 完了日時
);

-- インデックス作成
create index idx_task_completions_task_id on task_completions(task_id);

comment on table task_completions is 'タスク完了状態';
comment on column task_completions.id is 'タスク完了状態ID';
comment on column task_completions.task_id is 'タスクID';
comment on column task_completions.completed_at is '完了日時';

-- サブタスク完了状態テーブル
create table subtask_completions (
    id uuid primary key default gen_random_uuid(),
    subtask_id uuid not null references subtasks(id), -- サブタスクID
    completed_at timestamp not null default current_timestamp -- 完了日時
);

-- インデックス作成
create index idx_subtask_completions_subtask_id on subtask_completions(subtask_id);

comment on table subtask_completions is 'サブタスク完了状態';
comment on column subtask_completions.id is 'サブタスク完了状態ID';
comment on column subtask_completions.subtask_id is 'サブタスクID';
comment on column subtask_completions.completed_at is '完了日時';
