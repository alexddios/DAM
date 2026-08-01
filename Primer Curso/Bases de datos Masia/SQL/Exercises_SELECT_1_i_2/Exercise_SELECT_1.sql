-- 1. - Display all data in the club table.
select * from FB_CLUB;
-- 2. - List teams and their foundation year by seniority.
select TEAM_NAME,FOUNDATION from FB_CLUB
order by FOUNDATION;
-- 3. - Get the stadium, its capacity and its number of members.
select STADIUM_NAME,CAPACITY,COMMUNITY from FB_CLUB;
-- 4.- Display all players of a particular club.
select * from FB_PLAYER
where CLUB_NAME='Real Mallorca';
-- 5.Make a list of all the goalkeepers of all clubs (number equal to 1).
select NAME from FB_PLAYER
where PLAYER_NUMBER=1;
-- 6. - Display players, games played and the number of goals in descending order
select NAME,MATCHES,GOALS
from FB_STATISTICS join FB_PLAYER
on FB_STATISTICS.PLAYER_ID=FB_PLAYER.PLAYERID
order by FB_STATISTICS.GOALS;
--7. - Get all stadiums with more than 50000 spectators.
select STADIUM_NAME from FB_CLUB
where CAPACITY>=50000;
-- 8. Display all stadiums that were founded in the first half of the past century.
select STADIUM_NAME,FOUNDATION from FB_CLUB
where FOUNDATION  between 1900 and 1950;
-- 9. - Make a list of all players whose name starts with R.
select NAME from FB_PLAYER
where NAME like 'R%';
-- 10. - Get all players who have scored at least one goal and played 20 to 28 games, display the goals
-- that have scored. The list must be sorted by the games played.
select NAME,GOALS,MATCHES
from FB_STATISTICS join FB_PLAYER
on FB_STATISTICS.PLAYER_ID=FB_PLAYER.PLAYERID
where MATCHES between 20 and 28
order by MATCHES;
-- 11. - Display players who have 1 or 3 yellow cards.
select NAME,YELLOW_CARDS
from FB_STATISTICS join FB_PLAYER
on FB_STATISTICS.PLAYER_ID=FB_PLAYER.PLAYERID
where YELLOW_CARDS in(1,3);
-- 12. - Get the adversaries that has had a particular team
SELECT
    Match_Date,
    CASE
        WHEN Local_Team = 'Real Madrid' THEN Foreign_Team
        ELSE Local_Team
        END AS Adversary
FROM FB_CALENDAR
WHERE Local_Team = 'Real Madrid' OR Foreign_Team = 'Real Madrid'
ORDER BY Match_Date;

select FOREIGN_TEAM from FB_CALENDAR where LOCAL_TEAM='Real Mallorca'union
select LOCAL_TEAM from FB_CALENDAR where FOREIGN_TEAM='Real Mallorca';
-- 13. - Display players that are forwards or defenders.
select NAME,POSITION from FB_PLAYER
where POSITION in('Delantero','Defensa');
--14. - How many cards (yellow and red) were taken to payers of a certain team.
select CLUB_NAME,sum(YELLOW_CARDS),sum(RED_CARDS)
from FB_PLAYER join FB_STATISTICS
on FB_PLAYER.PLAYERID=FB_STATISTICS.PLAYER_ID
group by CLUB_NAME
having CLUB_NAME='Real Mallorca';
-- 15. - List the names of the players and the players' minutes played of a particular team.
select NAME,MINUTES
from FB_PLAYER join FB_STATISTICS
on FB_PLAYER.PLAYERID=FB_STATISTICS.PLAYER_ID
where CLUB_NAME='Real Mallorca';
-- 16. - Display players ages.
SELECT
    Name,Date_of_birth ,FLOOR(MONTHS_BETWEEN(SYSDATE, Date_of_birth) / 12) AS "Age"
FROM
    FB_PLAYER
ORDER BY "Age" DESC;
--17. - Make 3 queries of your election on these tables.
-- 17.1) Summary by club: number of players, total goals and average goals (SUM, COUNT, AVG) + HAVING
SELECT
    p.CLUB_NAME,
    COUNT(*)AS NUM_JUGADORES,
    SUM(s.GOALS)AS GOLES_TOTALES,
    ROUND(AVG(s.GOALS), 2)AS PROM_GOLES
FROM FB_PLAYER p
         JOIN FB_STATISTICS s
              ON s.PLAYER_ID = p.PLAYERID
GROUP BY p.CLUB_NAME
HAVING SUM(s.GOALS) > 0
ORDER BY GOLES_TOTALES DESC;


-- 17.2) Age (FLOOR + MONTHS_BETWEEN) and name in capital letters (UPPER) for players from a club
SELECT UPPER(p.NAME)AS NAME_UPPER,p.CLUB_NAME,FLOOR(MONTHS_BETWEEN(SYSDATE, p.DATE_OF_BIRTH) / 12) AS AGE
FROM FB_PLAYER p
WHERE p.CLUB_NAME = 'Real Mallorca'
ORDER BY AGE DESC, NAME_UPPER;


-- 17.3) Statistics by position: min/max/average minutes and total yellow cards (MIN, MAX, AVG, SUM)
SELECT p.POSITION,MIN(s.MINUTES)AS MIN_MINUTES,MAX(s.MINUTES)AS MAX_MINUTES,ROUND(AVG(s.MINUTES), 1)AS AVG_MINUTES,
SUM(s.YELLOW_CARDS)AS TOTAL_YELLOW
FROM FB_PLAYER p
         JOIN FB_STATISTICS s
    ON s.PLAYER_ID = p.PLAYERID
GROUP BY p.POSITION
ORDER BY AVG_MINUTES DESC;
