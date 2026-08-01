create table Desarrolladores(
    codigo integer primary key,
    nombre varchar2(255),
    pais varchar2(255)
);

create table Videojuegos(
    codigo integer primary key,
    nombre varchar2(255),
    CodigoDesarrollador integer,
    precio number(10,2),
    AnioLanzamiento integer,

    constraint fk_CodigoDesarrollador foreign key (CodigoDesarrollador) references Desarrolladores(codigo)
);

-- Inserciones para la tabla Desarrolladores
INSERT INTO Desarrolladores (Codigo, Nombre, Pais) VALUES (1, 'Nintendo', 'Japón');
INSERT INTO Desarrolladores (Codigo, Nombre, Pais) VALUES (2, 'Rockstar Games', 'EE.UU.');
INSERT INTO Desarrolladores (Codigo, Nombre, Pais) VALUES (3, 'CD Projekt', 'Polonia');
INSERT INTO Desarrolladores (Codigo, Nombre, Pais) VALUES (4, 'Ubisoft', 'Francia');

-- Inserciones para la tabla Videojuegos
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (1, 'Super Mario Odyssey', 1, 59.99, 2017);
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (2, 'Grand Theft Auto V', 2, 29.99, 2013);
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (3, 'The Witcher 3', 3, 39.99, 2015);
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (4, 'Cyberpunk 2077', 3, 49.99, 2020);
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (5, 'Assassin''s Creed Valhalla', 4, 59.99, 2020);
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (6, 'The Legend of Zelda: BOTW', 1, 59.99, 2017);
INSERT INTO Videojuegos (Codigo, Nombre, CodigoDesarrollador, Precio, AnioLanzamiento) VALUES (7, 'Red Dead Redemption 2', 2, 39.99, 2018);

-- 1
select nombre,AnioLanzamiento
from Videojuegos;

-- 2
select nombre, precio
from Videojuegos
where precio<=40;

-- 3
select *
from Desarrolladores
where pais='Japón';

-- 4
select round(avg(precio),2) as "Precio medio"
from Videojuegos;

-- 5
select Videojuegos.nombre,precio,D.nombre
from Videojuegos left join Desarrolladores D on Videojuegos.CodigoDesarrollador = D.codigo;

