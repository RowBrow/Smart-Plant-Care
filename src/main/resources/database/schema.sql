drop table if exists measurement;
drop table if exists device;
drop table if exists profile;

create table measurement
(
    id INTEGER PRIMARY KEY,
    device_id REFERENCES device(id),
    timestamp TIMESTAMP,
    light    INTEGER,
    temp     FLOAT,
    water    INTEGER,
    humidity FLOAT
);

create table device
(
    id TEXT PRIMARY KEY,
    name TEXT,
    active_profile REFERENCES profile(id)
);

create table profile
(
    id INTEGER PRIMARY KEY,
    name TEXT,
    ideal_temp FLOAT,
    max_temp FLOAT,
    min_temp FLOAT,
    ideal_light INTEGER,
    max_light INTEGER,
    min_light INTEGER,
    ideal_water INTEGER,
    max_water INTEGER,
    min_water INTEGER,
    ideal_humidity FLOAT,
    max_humidity FLOAT,
    min_humidity FLOAT,
    expiry_date TIMESTAMP
);