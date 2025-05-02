drop table if exists measurement;

create table measurement
(
    device_id INTEGER,
    datetime timestamp
        primary key,
    light    FLOAT,
    temp     FLOAT,
    water    FLOAT,
    humidity FLOAT
);
