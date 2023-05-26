create schema github;

create table github.repositories (
    id int auto_increment,
    id_repo int not null unique,
    owner varchar(30),
    name_repo varchar(60),
    created_at date,
    stars int,
    forks int,
    primary key (id, id_repo)
);