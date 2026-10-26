--liquibase formatted sql

--changeset magofrays:statistic-dedupe
DELETE FROM statistic a
    USING statistic b
WHERE a.type = b.type
  AND a.scope = b.scope
  AND a.event_type = b.event_type
  AND COALESCE(a.task_id, '00000000-0000-0000-0000-000000000000'::uuid) =
      COALESCE(b.task_id, '00000000-0000-0000-0000-000000000000'::uuid)
  AND COALESCE(a.user_id, '00000000-0000-0000-0000-000000000000'::uuid) =
      COALESCE(b.user_id, '00000000-0000-0000-0000-000000000000'::uuid)
  AND (a.updated_at < b.updated_at
       OR (a.updated_at = b.updated_at AND a.id < b.id));

--changeset magofrays:statistic-unique-index
CREATE UNIQUE INDEX uq_statistic_dims ON statistic (
    COALESCE(task_id, '00000000-0000-0000-0000-000000000000'::uuid),
    COALESCE(user_id, '00000000-0000-0000-0000-000000000000'::uuid),
    type,
    scope,
    event_type
);
