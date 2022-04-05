create table currency
(
    id   serial      not null primary key,
    code varchar(25) not null
);

create unique index on currency (code);

create table category
(
    id   serial      not null primary key,
    name varchar(30) not null
);

create unique index on category (name);

create table finance_operation
(
    id            bigserial not null primary key,
    account_id    integer   not null,
    date          date      not null,
    currency_id   integer   not null
        references currency,
    amount        numeric   not null,
    type          text      not null,
    repeat_period text      not null,
    category_id   integer   not null
        references category
);
