create table account
(
    id       serial       not null primary key,
    email    varchar(256) not null,
    password text         not null
);

create unique index on account(email);
