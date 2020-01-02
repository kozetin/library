create table usr (
    id int8 not null,
    login varchar(255) not null,
    password varchar(255) not null,
    active boolean not null,
    primary key (id)
);

create table book (
    id int8 not null,
    ISN int8 not null,
    author varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

create table book_user (
    user_id int8 not null,
    book_id int8 not null
);

alter table if exists book_user
    add constraint book_user_fk
    foreign key (user_id) references usr;