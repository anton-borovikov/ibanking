create table account (
  id  bigserial not null,
  account_balance float8,
  account_number varchar(255),
  user_id int8,
  primary key (id));

create table branch (
  id  bigserial not null,
  branch_address varchar(255),
  branch_name varchar(255),
  city_id int8,
  primary key (id));

create table city (
  id  bigserial not null,
  city_name varchar(255),
  primary key (id));

create table role (
  id  bigserial not null,
  name varchar(255),
  primary key (id));

create table service (
  id  bigserial not null,
  service_discription varchar(255),
  service_name varchar(255),
  primary key (id));

create table user_role (
  user_id int8 not null,
  role_id int8 not null,
  primary key (user_id, role_id));

create table usr (
  id  bigserial not null,
  activated boolean not null,
  activation_code varchar(255),
  age int4 not null,
  first_name varchar(255),
  last_name varchar(255),
  password varchar(255),
  user_email varchar(255),
  username varchar(255),
  primary key (id));

alter table if exists account
  add constraint account_user_fk
foreign key (user_id) references usr;

alter table if exists branch
  add constraint branch_city_fk
foreign key (city_id) references city;

alter table if exists user_role
  add constraint user_role_role_fk
foreign key (role_id) references role;

alter table if exists user_role
  add constraint user_role_user_fk
foreign key (user_id) references usr;