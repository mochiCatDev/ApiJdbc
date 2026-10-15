-- Crear la tabla
create table videojuegos(
	codigo varchar(10) primary key,
	nombre varchar(100) not null,
	plataforma varchar(50) not null,
	precio double precision not null,
	disponible boolean not null,
	genero varchar(50)
)