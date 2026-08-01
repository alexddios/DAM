-- 1
INSERT INTO CO_CUSTOMER
(DNI, NAME, PROVINCE_CODE, TYPE, REGISTER_DATE, VENDOR_CODE, TOTAL_PURCHASES)
VALUES
    (:dni, :name, :province_code, :type, TO_DATE(:register_date, 'DD/MM/RR'), :vendor_code, :total_purchases);

-- 2 The name of each customer with their purchases (in thousands)
select NAME,round(TOTAL_PURCHASES,-3) as "Rounded in thousands",(trunc(TOTAL_PURCHASES/1000)) from CO_CUSTOMER;

-- 3  List of customers sorted by customer type and name (in descending order)
select NAME,TYPE from CO_CUSTOMER order by TYPE desc,NAME desc ;

-- 4 List of customers of type 'AA' to be in Zaragoza and Barcelona
select NAME from CO_CUSTOMER where TYPE='AA' and PROVINCE_CODE in (select CODE from CO_PROVINCE where NAME in ('ZARAGOZA','BARCELONA'));

-- 5. Name of the provinces where there are customers
select p.NAME from CO_CUSTOMER c join CO_PROVINCE p on c.PROVINCE_CODE= p.NAME;

-- 6.  Total Zaragoza's customer purchases
select sum(TOTAL_PURCHASES) from CO_CUSTOMER where PROVINCE_CODE=(select CODE from CO_PROVINCE where NAME='ZARAGOZA');

-- 7. Minimum, Maximum, Average from the purchases of our clients
select min(TOTAL_PURCHASES),max(TOTAL_PURCHASES),avg(TOTAL_PURCHASES) from CO_CUSTOMER;

-- 8. How many customers and how many different types of customers are in the province of Zaragoza
select count(*),count(distinct TYPE) from CO_CUSTOMER where PROVINCE_CODE='ZA';

-- 9. How many days has been each customer in our table (the data should not have decimal)
select count(distinct TO_CHAR(REGISTER_DATE,'DD')) from CO_CUSTOMER;

-- 10. Sum of purchases by province
select PROVINCE_CODE,sum(TOTAL_PURCHASES) from CO_CUSTOMER group by PROVINCE_CODE;

-- 11. How many clients of each type is in each province. The list is sorted by the total amount of
-- purchases from each group
select TYPE,PROVINCE_CODE,count(*) from CO_CUSTOMER group by TYPE,PROVINCE_CODE order by count(*) desc;

-- 12. Report how many days has the oldest client in each provinces.
select PROVINCE_CODE,min(TO_CHAR(REGISTER_DATE,'DD')) from CO_CUSTOMER group by PROVINCE_CODE;

-- 13. How many clients are in each group and what is the total amount of his/her purchases, as long as this
-- amount exceeds 150000.
select PROVINCE_CODE,count(*),sum(TOTAL_PURCHASES) from CO_CUSTOMER
where TOTAL_PURCHASES>150000 group by PROVINCE_CODE;

-- 14.  Erase the customers of the province 'MA'
delete from CO_CUSTOMER where PROVINCE_CODE='MA';
commit;

-- 15. Increase 10% of the value of customers' purchases of type 'AA'
update CO_CUSTOMER set TOTAL_PURCHASES=TOTAL_PURCHASES*1.1 where TYPE='AA';
commit;

-- 16. create a sequence to insert the table five article items with codes: 1010, 1020,
-- 1030, 1040 and 1050.
create sequence seq_item_code start with 1010 increment by 1 nocycle ;
commit;
insert into CO_ITEM (CODE,DESCRIPTION,PRICE,STOCK) values (seq_item_code.nextval,'item1',10,100);
commit;
insert into CO_ITEM (CODE,DESCRIPTION,PRICE,STOCK) values (seq_item_code.nextval,'item2',20,200);

create sequence seq_item_code start with 1030 increment by 10 nocycle;
insert into CO_ITEM (CODE,DESCRIPTION,PRICE,STOCK) values (seq_item_code.nextval,'item3',30,300);
insert into CO_ITEM (CODE,DESCRIPTION,PRICE,STOCK) values (seq_item_code.nextval,'item4',40,400);
insert into CO_ITEM (CODE,DESCRIPTION,PRICE,STOCK) values (seq_item_code.nextval,'item5',50,500);