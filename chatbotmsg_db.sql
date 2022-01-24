drop database chatbotmsgdb;
drop user chatbotmsg;
create user chatbotmsg with password 'password';
create database chatbotmsgdb with template=template0 owner=chatbotmsg;
\connect chatbotmsgdb;
alter default privileges grant all on tables to chatbotmsg;
alter default privileges grant all on sequences to chatbotmsg;

create table messages(
message_id integer primary key not null,
customer_id text not null,
dialog_id text not null,
message text not null,
language text not null,
consent boolean not null default false
);


create sequence messages_seq increment 1 start 1;