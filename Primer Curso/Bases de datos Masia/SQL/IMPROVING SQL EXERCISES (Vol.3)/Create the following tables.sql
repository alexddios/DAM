create table Vol3_Pieces(
    Code integer primary key ,
    Name varchar2(255)

);
create table Vol3_Providers(
    Code varchar2(255) primary key ,
    Name varchar2(255)
);
CREATE TABLE Vol3_Provides (
    Piece INTEGER,
    Provider varchar2(255),
    Price INTEGER,
    PRIMARY KEY (Piece, Provider),
    constraint FK_Piece
    FOREIGN KEY (Piece)
    REFERENCES Vol3_Pieces(Code),
    constraint FK_Provider
    FOREIGN KEY (Provider)
    REFERENCES Vol3_Providers(Code)
);