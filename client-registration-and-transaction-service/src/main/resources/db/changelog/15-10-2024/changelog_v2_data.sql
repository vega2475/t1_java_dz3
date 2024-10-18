insert into client (first_name, last_name, middle_name)
VALUES ('david', 'chernykh', 'sergeevich');

insert into account (client_id, type, balance) VALUES (1, 'DEPOSIT', 100.00);
insert into account (client_id, type, balance) VALUES (1, 'CREDIT', 1000.00);