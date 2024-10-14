alter table transaction
    add column sender_account_id BIGINT references account(id),
    add column receiver_account_id BIGINT references account(id);