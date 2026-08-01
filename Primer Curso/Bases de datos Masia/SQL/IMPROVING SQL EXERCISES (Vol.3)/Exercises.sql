-- 1 Select the name of all the pieces.
select *
from VOL3_PIECES;

-- 2 Select all the providers' data.
select *
from VOL3_PROVIDERS;

-- 3 Obtain the average price of each piece (show only the piece code and the
-- average price).
select PIECE,round(avg(PRICE),2)
from VOL3_PROVIDES
group by PIECE;

-- 4 Obtain the names of all providers who supply piece 1.
-- a. With a Subquery
-- b. Without a Subquery
select NAME from VOL3_PROVIDERS where CODE in (select PROVIDER from VOL3_PROVIDES where PIECE=1);
select NAME from VOL3_PROVIDERS inner join VOL3_PROVIDES on VOL3_PROVIDERS.CODE=VOL3_PROVIDES.PROVIDER where PIECE=1;

-- 5 Select the name of pieces provided by provider with code "HAL".
-- a. Without a Subquery
-- b. With a Subquery
-- c. With an EXISTS

select VOL3_PIECES.NAME
FROM VOL3_PIECES
INNER JOIN VOL3_PROVIDES ON VOL3_PIECES.CODE = VOL3_PROVIDES.PIECE
WHERE VOL3_PROVIDES.PROVIDER = 'HAL';
SELECT NAME
FROM VOL3_PIECES
WHERE CODE IN (
    SELECT PIECE
    FROM VOL3_PROVIDES
    WHERE PROVIDER = 'HAL'
);
SELECT NAME
FROM vol3_PIECES
WHERE EXISTS (
    SELECT 1
    FROM VOL3_PROVIDES
    WHERE VOL3_PROVIDES.PIECE = vol3_PIECES.CODE
      AND VOL3_PROVIDES.PROVIDER = 'HAL'
);

-- 6 For each piece, find the most expensive offering of that piece and include the
-- piece name, provider name, and price (note that there could be two providers
-- who supply the same piece at the most expensive price).
select NAME from VOL3_PIECES join VOL3_PROVIDES
on VOL3_PIECES.CODE=VOL3_PROVIDES.PIECE
where PRICE in (select max(PRICE) from VOL3_PROVIDES);

-- 7 Add an entry to the database to indicate that "Skellington Supplies" (code
-- "TNBC") will provide sprockets (code "1") for 7 cents each.
insert into VOL3_PROVIDES (PIECE,PROVIDER,PRICE) values (1,'TNBC',7);
commit;

-- 8 Increase all prices by one cent.
update VOL3_PROVIDES set PRICE=PRICE+1;
commit;

-- 9 Update the database to reflect that "Susan Calvin Corp." (code "RBT") will not
-- supply bolts (code 4).
update VOL3_PROVIDES set PRICE=NULL where PIECE=4 and PROVIDER='RBT';
commit;

-- 10 Update the database to reflect that "Susan Calvin Corp." (code "RBT") will not
-- supply any pieces (the provider should still remain in the database).
update VOL3_PROVIDES set PRICE=NULL where PIECE=NULL and PROVIDER='RBT';


