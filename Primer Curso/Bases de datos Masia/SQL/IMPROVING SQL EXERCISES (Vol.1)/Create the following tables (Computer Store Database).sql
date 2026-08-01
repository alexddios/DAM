create table Manufacturers(
    Code int primary key,
    Name varchar2(50)
);

create table Products(
    Code int primary key,
    Name varchar2(50),
    Price number(10,2),
    Manufacturer int,

    constraint FK_Manufacturers
    foreign key (Manufacturer)
    references Manufacturers(Code)

);


