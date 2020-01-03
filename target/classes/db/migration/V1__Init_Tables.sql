create table usr (
    id int8 serial,
    login varchar(255) not null,
    password varchar(255) not null,
    active boolean not null,
    user_id int8,
    primary key (id)
);

create table book (
    id int8 serial,
    ISN int8 not null,
    author varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);