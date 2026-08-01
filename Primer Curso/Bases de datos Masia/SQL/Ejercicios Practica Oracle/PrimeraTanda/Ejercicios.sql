-- 1
select * from FB_CLUB;
-- 2
select NAME,POSITION from FB_PLAYER
where CLUB_NAME like 'FC Barcelona';

-- 3
select STADIUM_NAME from FB_CLUB
where CAPACITY >50000;

-- 4
select distinct POSITION from FB_PLAYER;

-- 5
select NAME,WEIGHT from FB_PLAYER
order by WEIGHT desc;

-- 6
select p.NAME,s.GOALS from FB_PLAYER p
join FB_STATISTICS s on p.PLAYERID=s.PLAYER_ID;

-- 7
select p.NAME,s.GOALS
from FB_PLAYER p join FB_STATISTICS s on p.PLAYERID=s.PLAYER_ID
where s.GOALS between 5 and 15;

-- 8
select TEAM_NAME from FB_CLUB
where TEAM_NAME like 'Real%';

-- 9
select count(*),CLUB_NAME from FB_PLAYER
group by CLUB_NAME;

-- 10
select p.CLUB_NAME from FB_STATISTICS s join FB_PLAYER p on p.PLAYERID=s.PLAYER_ID
group by CLUB_NAME having avg(s.GOALS) >5;

-- 11
select p.NAME,c.TEAM_NAME,s.YELLOW_CARDS
from FB_STATISTICS s join FB_PLAYER p on s.PLAYER_ID=p.PLAYERID
join FB_CLUB c on p.CLUB_NAME=c.TEAM_NAME
where c.FOUNDATION>1905;

-- 12
select NAME from FB_PLAYER p where CLUB_NAME in
(select c.TEAM_NAME from FB_CLUB c where c.COMMUNITY>100000);

-- 13
select
from FB_CLUB left join FB_STATISTICS