drop table if exists measurement;
drop table if exists device;
drop table if exists profile;

create table measurement
(
    id INTEGER PRIMARY KEY,
    device_id REFERENCES device(id),
    timestamp TIMESTAMP,
    light    FLOAT,
    temp     FLOAT,
    water    FLOAT,
    humidity FLOAT
);

create table device
(
    id TEXT PRIMARY KEY ,
    name TEXT
);

/* THIS IS INCOMPLETE */
create table profile
(
    id INTEGER PRIMARY KEY,
    name TEXT,
    ideal_temp FLOAT
)