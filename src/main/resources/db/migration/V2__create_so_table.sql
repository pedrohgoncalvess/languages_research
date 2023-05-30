create schema if not exists stackoverflow;

create table if not exists stackoverflow.questions (
    id int auto_increment,
    id_question int unique,
    title varchar(175),
    view_count int,
    answer_count int,
    score int,
    is_answered boolean,
    creation_date date,
    last_activity date,
    inserted_at datetime default now(),

        primary key (id,id_question)
);

create table if not exists stackoverflow.questions_tags (
    id int auto_increment,
    id_compost varchar(40) unique,
    id_question int,
    tag_question varchar(25),

    primary key (id, id_compost),
    foreign key (id_question)
        references stackoverflow.questions(id_question)
              on delete cascade
);

create table if not exists stackoverflow.pagination  (
    id int auto_increment,
    id_compost varchar(40) unique,
    searched_tag varchar(20),
    searched_page int,
    searched_at datetime default current_timestamp,
        primary key (id,id_compost)
)