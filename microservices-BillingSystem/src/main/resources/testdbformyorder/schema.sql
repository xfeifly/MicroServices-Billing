drop table T_MYORDER if exists;

create domain paystatus as varchar default 'Unpaid' check value in('Unpaid', 
'Paid', 'Unsuccessful'); 

create table T_MYORDER (ID bigint identity primary key, USERID varchar(9),
                        PRODUCTNAME varchar(50) not null, PRICE decimal(8,2), STATUS paystatus, unique(USERID));
                        
ALTER TABLE T_MYORDER ALTER COLUMN PRICE SET DEFAULT 0.0;
