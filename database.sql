create table studenter (
    id serial PRIMARY KEY,
    fornavn varchar(50) NOT NULL,
    etternavn varchar(50) NOT NULL,
);

create table emner (
  emne_id serial primary key,
  navn varchar(10) not null unique check (navn = 'R1' or navn = 'R2' or navn = 'S1'
            or navn = 'S2' or navn = 'Fysikk1' or navn = 'Fysikk2'
            or navn = 'Kjemi1' or navn = 'Kjemi2')
);

create table student_emner (
  student_id integer not null,
  emne_id integer not null,
  emne_start timestamp not null,
  primary key (student_id, emne_id),
  foreign key (student_id) references students (id) match simple
    on update no action on delete restrict,
  foreign key (emne_id) references emner (emne_id) match simple
   on update no action on delete restrict
);

create table betaling (
  betaling_id serial primary key,
  student_id integer not null,
  emne_id integer not null,
  belop integer not null,
  dato timestamp not null,
  foreign key (student_id, emne_id) references student_emner (student_id, emne_id) match simple
    on update no action on delete restrict
);
