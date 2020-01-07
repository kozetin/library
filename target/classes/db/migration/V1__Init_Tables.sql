create table usr (
    id int8 serial,
    login varchar(255) not null,
    password varchar(255) not null,
    active boolean not null,
    primary key (id)
);

create table book (
    id int8 serial,
    ISN int8 not null,
    author varchar(255) not null,
    name varchar(255) not null,
    user_name varchar(255),
    primary key (id)
);