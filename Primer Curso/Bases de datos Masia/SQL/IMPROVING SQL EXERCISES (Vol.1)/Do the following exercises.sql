-- 1
select NAME from PRODUCTS;

-- 2
select NAME,PRICE from PRODUCTS;

-- 3
select NAME from PRODUCTS  where PRICE<=200;

-- 4
select * from PRODUCTS where PRICE between 60 and 200;

-- 5
select NAME,PRICE*100 as "Price in cents"from PRODUCTS;

-- 6
select avg(PRICE) from PRODUCTS;

-- 7
select avg(PRICE) from PRODUCTS where MANUFACTURER=2;

-- 8
select count(*) from PRODUCTS where PRICE >=180;

-- 9
select NAME,PRICE from PRODUCTS where PRICE>=180 order by PRICE desc,NAME asc;

-- 10
select * from PRODUCTS join MANUFACTURERS on PRODUCTS.MANUFACTURER=MANUFACTURERS.CODE;

-- 11
select PRODUCTS.NAME,PRICE,MANUFACTURERS.NAME
from PRODUCTS join MANUFACTURERS on PRODUCTS.MANUFACTURER=MANUFACTURERS.CODE;

-- 12
select MANUFACTURER,avg(PRICE) as "Average Price" from PRODUCTS group by MANUFACTURER;

-- 13
select avg(PRICE) as "Average price",manufacturers.NAME
from PRODUCTS join manufacturers on PRODUCTS.MANUFACTURER=MANUFACTURERS.CODE
group by manufacturers.NAME;

-- 14
select MANUFACTURERS.NAME,avg(PRICE) as "AvergaePrice" from PRODUCTS
join MANUFACTURERS on PRODUCTS.MANUFACTURER=MANUFACTURERS.CODE
group by MANUFACTURERS.NAME
having avg(PRICE)>=150;

-- 15
select NAME,PRICE from PRODUCTS where PRICE =
(select min(PRICE) from PRODUCTS);

-- 16
select  MANUFACTURERS.NAME,PRODUCTS.NAME, PRICE as "Max Price"
from PRODUCTS
join MANUFACTURERS
on PRODUCTS.MANUFACTURER = MANUFACTURERS.CODE
where PRICE =
(select max(PRICE) from PRODUCTS p2 where p2.MANUFACTURER = PRODUCTS.MANUFACTURER);
;

-- 17
INSERT INTO Products(Code,Name,Price,Manufacturer)
VALUES(11,'Loudspeakers',70,2);

-- 18
update PRODUCTS set NAME='Laser Printer' where CODE=8;

-- 19
update PRODUCTS set PRICE=PRICE*0.9;

-- 20
update PRODUCTS set PRICE=PRICE*0.9 where PRICE>=120;
