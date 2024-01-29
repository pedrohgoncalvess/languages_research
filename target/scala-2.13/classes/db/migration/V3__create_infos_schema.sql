create schema if not exists infos;

create table if not exists infos.programming_languages(
    id bigserial,
    programming_language varchar(15),
    package varchar(50) unique,
    "type" varchar(10),
        primary key (id)
);

create table if not exists infos.tecnologies(
    id bigserial,
    tecnologie varchar(35) unique,
    "type" varchar(50),
        primary key (id)
);