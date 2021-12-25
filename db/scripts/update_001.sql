create table if not exists users (
                                     id serial primary key,
                                     username varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(100) not null
    );

create table if not exists items (
                                      id serial primary key,
                                      created timestamp,
                                      description varchar(500) not null,
                                      done boolean,
                                      user_id int references users(id)
);
create table if not exists candidates (
                                     id serial primary key,
                                     name varchar(100) not null,
                                     experience varchar(100) not null,
                                     salary int not null
);