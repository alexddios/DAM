-- 1
select NAME,concat((100*MINUTES)/(MATCHES * 90),' %') AS pct_minutes,GOALS  from FB_STATISTICS
join FB_PLAYER p on p.PLAYERID = PLAYER_ID
order by GOALS,RED_CARDS;

-- With one decimal
select NAME,concat(round(100*MINUTES/(MATCHES * 90), 1),' %') AS pct_minutes,GOALS  from FB_STATISTICS
join FB_PLAYER p on p.PLAYERID = PLAYER_ID
order by GOALS,RED_CARDS;

-- 2
select TEAM_NAME,NVL(FOUNDATION,1890) as "Fundation" from FB_CLUB;

-- 3
select STADIUM_NAME,TEAM_NAME,round(CAPACITY,-2) as "Capacity rounded to hundreds" from FB_CLUB;

-- 4 Obtener el club, su estadio y la diferencia entre la capacidad y la capacidad media de todos los clubes.
select TEAM_NAME,STADIUM_NAME,
       (CAPACITY-(select avg(CAPACITY) from FB_CLUB)) as "Difference capacity" from FB_CLUB;
-- 4.a. - ¿Cómo podemos evitar que aparezca el signo menos en la última columna?
select TEAM_NAME,STADIUM_NAME,
       abs(CAPACITY-(select avg(CAPACITY) from FB_CLUB)) as "Difference capacity" from FB_CLUB;

-- 5 Enumera el club y el estadio con mayor número de socios.
select TEAM_NAME,STADIUM_NAME from FB_CLUB order by COMMUNITY desc fetch first 1 row only;

-- 6 ¿Qué harías para encontrar el número de clubes que tenemos en la base de datos? Y
-- el número de clubes que tienen el año de fundación distinto de NULL?
select count(TEAM_NAME) from FB_CLUB;
select count(TEAM_NAME) from FB_CLUB
where FOUNDATION is not null ;

-- 7
select sum(YELLOW_CARDS),sum(RED_CARDS),sum(GOALS) from FB_STATISTICS;

-- 8
select count(PLAYERID) as "Number of players",CLUB_NAME from FB_PLAYER
group by CLUB_NAME
having count(PLAYERID)>=5;

--9 Mostrar los jugadores, el número de partidos jugados, el número de goles marcados y
-- el mayor valor entre partidos jugados y goles marcados.
select NAME,MATCHES,GOALS,GREATEST(MATCHES, GOALS) AS higher_value from FB_STATISTICS
join ALEX.FB_PLAYER FP on FP.PLAYERID = FB_STATISTICS.PLAYER_ID



