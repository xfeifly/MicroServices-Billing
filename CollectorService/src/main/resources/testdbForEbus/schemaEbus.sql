drop table E_ACCOUNT if exists;

create table E_ACCOUNT (ID bigint identity primary key, IDNUMBER VARCHAR(9), ENUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(ENUMBER));
                        
ALTER TABLE E_ACCOUNT ALTER COLUMN BALANCE SET DEFAULT 0.0;
