create table account
(
    account_id big_int primary key,
    user_id    varchar(20)  not null unique,
    password   varchar(100) not null,
    email      varchar(50)  not null,
    status     varchar(20)  not null
);