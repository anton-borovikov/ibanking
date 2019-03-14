CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO role (name)
VALUES ('user');

INSERT INTO usr (username, password, first_name, last_name, age, user_email, activated)
VALUES ('user1', crypt('111', gen_salt('bf', 8)), 'Lagutin', 'Vadim', '45', '', TRUE),
  ('user2', crypt('222', gen_salt('bf', 8)), 'Ivanov', 'Ivan', '31', '', TRUE);

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1), (2, 1);
