alter table finance_operation
    rename column repeat_period to period;

alter table finance_operation
    alter column date drop not null;
