create schema if not exists github;

create table if not exists github.repositories (
    id int auto_increment,
    id_repo int not null unique,
    owner varchar(30),
    name_repo varchar(60),
    created_at date,
    stars int,
    forks int,
    inserted_at datetime default current_timestamp,
        primary key (id, id_repo)
);