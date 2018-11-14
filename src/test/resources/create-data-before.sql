CREATE EXTENSION IF NOT EXISTS pgcrypto;

DELETE FROM user_role;
DELETE FROM account;
DELETE FROM usr;
DELETE FROM role;
DELETE FROM branch;
DELETE FROM city;

ALTER SEQUENCE public.usr_id_seq RESTART WITH 1;
ALTER SEQUENCE public.role_id_seq RESTART WITH 1;
ALTER SEQUENCE public.role_id_seq RESTART WITH 1;
ALTER SEQUENCE public.account_id_seq RESTART WITH 1;
ALTER SEQUENCE public.city_id_seq RESTART WITH 1;
ALTER SEQUENCE public.branch_id_seq RESTART WITH 1;

INSERT INTO role (name)
VALUES ('user');

INSERT INTO usr (username, password, first_name, last_name, age, user_email, activated) VALUES
  ('user1', crypt('111', gen_salt('bf', 8)), 'Lagutin', 'Vadim', '45', 'jcoder3@gmail.com', TRUE),
  ('user2', crypt('222', gen_salt('bf', 8)), 'Ivanov', 'Ivan', '31', 'jcoder3@gmail.com', TRUE);

INSERT INTO user_role (user_id, role_id) VALUES
  (1, 1),
  (2, 1);

INSERT INTO account (account_number, account_balance, user_id) VALUES
  ('407810001', 100000, 1),
  ('407810002', 15000, 1),
  ('407810003', 200000, 2),
  ('407810004', 25000, 2);

INSERT INTO city (city_name) VALUES
  ('Moscow'),
  ('London'),
  ('Paris');

INSERT INTO branch (branch_name, branch_address, city_id) VALUES
  ('Moscow branch 1', 'Moscow address1', 1),
  ('Moscow branch 2', 'Moscow address2', 1),
  ('London branch 1', 'London address1', 2),
  ('Paris branch 1', 'Paris address1', 3);