drop table T_ACCOUNT if exists;

create table T_ACCOUNT (ID bigint identity primary key, IDNUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(IDNUMBER));
                        
ALTER TABLE T_ACCOUNT ALTER COLUMN BALANCE SET DEFAULT 0.0;
