drop database chatbotmsgdb;
drop user chatbotmsg;
create user chatbotmsg with password 'password';
create database chatbotmsgdb with template=template0 owner=chatbotmsg;
\connect chatbotmsgdb;
alter default privileges grant all on tables to chatbotmsg;
alter default privileges grant all on sequences to chatbotmsg;

create table messages(
message_id bigint primary key not null,
customer_id bigint not null,
dialog_id bigint not null,
message text not null,
language varchar(3) not null,
created timestamp not null
);

create table consents(
dialog_id bigint not null
);

create sequence messages_seq increment 1 start 1;