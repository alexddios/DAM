CREATE TABLE Club (
    Team_Name VARCHAR(100) PRIMARY KEY NOT NULL,
    Stadium_Name VARCHAR(100),
    Capacity INT,
    Community INT,
    Foundation INT NOT NULL 
);
CREATE TABLE Player(
    PlayerID INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Date_of_birth DATE,
    Weight DECIMAL(5,2),
    Player_Number INT,
    Position VARCHAR(50),

    Club_Name VARCHAR(100),            
    CONSTRAINT fk_club_player         
    FOREIGN KEY (Club_Name) 
    REFERENCES Club(Team_Name)     
);
CREATE TABLE Calendar(
    UniqueID INT PRIMARY KEY,
    Match_Date DATE NOT NULL,
    Local_Goals INT,
    Foreign_Goals INT,

    Local_Team VARCHAR(100),
    CONSTRAINT Play_at_Home
    FOREIGN KEY (Local_Team)
    REFERENCES Club(Team_Name),

    Foreign_Team VARCHAR(100),
    CONSTRAINT Play_as_a_Visitant
    FOREIGN KEY (Foreign_Team)
    REFERENCES Club(Team_Name)
);
CREATE TABLE Statistics (
    Player_ID INT PRIMARY KEY,        
    Goals INT DEFAULT 0,               
    Matches INT DEFAULT 0,              
    Minutes INT DEFAULT 0,             
    Yellow_Cards INT DEFAULT 0,         
    Red_Cards INT DEFAULT 0,           

    
    CONSTRAINT fk_stat_player 
    FOREIGN KEY (Player_ID) 
    REFERENCES Player(PlayerID)        
);
