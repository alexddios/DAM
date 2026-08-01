INSERT INTO CLUB (TEAM_NAME, STADIUM_NAME, CAPACITY, COMMUNITY, FOUNDATION)
VALUES ('Real Mallorca', 'Son Moix', 26020, 23000, 1916);

INSERT INTO CLUB (TEAM_NAME, STADIUM_NAME, CAPACITY, COMMUNITY, FOUNDATION)
VALUES ('Valencia CF', 'Mestalla', 49430, 40000, 1919);


INSERT INTO Player (PlayerID, Name, Date_of_birth, Weight, Player_Number, Position, Club_Name)
VALUES (1, 'Antonio Raillo', TO_DATE('08-10-1991', 'DD-MM-YYYY'), 80.0, 21, 'Defensa', 'Real Mallorca');

INSERT INTO Player (PlayerID, Name, Date_of_birth, Weight, Player_Number, Position, Club_Name)
VALUES (2, 'Gayà', TO_DATE('25-05-1995', 'DD-MM-YYYY'), 70.5, 14, 'Defensa', 'Valencia CF');


INSERT INTO STATISTICS (PLAYER_ID, Goals, MATCHES, MINUTES, YELLOW_CARDS, RED_CARDS)
VALUES (1, 0, 17, 1530, 2, 0);

INSERT INTO STATISTICS (PLAYER_ID, Goals, MATCHES, MINUTES, YELLOW_CARDS, RED_CARDS)
VALUES (2, 2, 30, 2500, 5, 0);


INSERT INTO CALENDAR (UNIQUEID, MATCH_DATE, LOCAL_GOALS, FOREIGN_GOALS, LOCAL_TEAM, FOREIGN_TEAM)
VALUES (1, TO_DATE('19-12-2025', 'DD-MM-YYYY'), 1, 1, 'Valencia CF', 'Real Mallorca');

SELECT 
    p.Name, 
    p.Player_Number, 
    s.Goals, 
    s.Matches, 
    s.Yellow_Cards
FROM Player p
INNER JOIN Statistics s ON p.PlayerID = s.Player_ID;
SELECT * FROM PLAYER;
COMMIT;