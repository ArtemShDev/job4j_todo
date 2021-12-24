create table if not exists items (
                                      id serial primary key,
                                      created timestamp,
                                      description text not null,
                                      done boolean
);
