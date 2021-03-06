drop table T_MYORDER if exists;

--create domain status as varchar(9) default 'Unpaid' check (value in('Unpaid', 'Paid', 'Unsucc')); 

--create table T_MYORDER (ID bigint identity primary key, USERID varchar(4),
--                        PRODUCTNAME varchar(50) not null, PRICE decimal(8,2), paystatus status);
                        
create table T_MYORDER (ID bigint identity primary key, USERID varchar(4),
                        PRODUCTNAME varchar(50) not null, PRICE decimal(8,2), PAYSTATUS varchar(9) default 'Unpaid');
ALTER TABLE T_MYORDER ALTER COLUMN PRICE SET DEFAULT 0.0;
