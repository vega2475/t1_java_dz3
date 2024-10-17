alter table transaction
    add column created_at TIMESTAMP default CURRENT_TIMESTAMP,
    add column type varchar(10);