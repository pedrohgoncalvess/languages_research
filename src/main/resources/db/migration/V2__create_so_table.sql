create schema stackoverflow;

create table stackoverflow.questions (
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

create table stackoverflow.questions_tags (
    id int auto_increment,
    id_question int,
    tag_question varchar(25),

    primary key (id),
    foreign key (id_question)
        references stackoverflow.questions(id_question)
              on delete cascade
);