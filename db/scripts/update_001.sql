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

CREATE TABLE IF NOT EXISTS drivers (
    id serial primary key,
    name varchar(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS engines (
                                       id serial primary key,
                                       numberengine varchar(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS cars (
                                       id serial primary key,
                                       vin varchar(100) NOT NULL,
                                       engine_id integer NOT NULL REFERENCES engines(id)
    );

CREATE TABLE IF NOT EXISTS history_owner (
                                             id serial primary key,
                                             driver_id integer NOT NULL REFERENCES drivers(id),
    car_id integer NOT NULL REFERENCES cars(id),
    CONSTRAINT history_owner_pkey PRIMARY KEY (driver_id, car_id)
    );