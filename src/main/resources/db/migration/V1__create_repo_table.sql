create schema if not exists github;

create table if not exists github.repositories (
    id bigserial,
    id_repo int not null unique,
    "owner" varchar(30),
    name_repo varchar(60),
    created_at date,
    stars int,
    forks int,
    inserted_at timestamp default now(),
        primary key (id, id_repo)
);

create table if not exists github.repositories_tags(
    id bigserial,
    id_compost varchar(70) unique,
    id_repo int,
    "tag" varchar(50),
        primary key (id, id_compost),
            foreign key (id_repo) references github.repositories(id_repo)
);

create table if not exists github.repositories_languages(
    id bigserial,
    id_compost varchar(70) unique,
    id_repo int,
    "language" varchar(50),
    use_language int,
    representativity numeric,
        primary key (id, id_compost),
            foreign key (id_repo) references github.repositories(id_repo)
);