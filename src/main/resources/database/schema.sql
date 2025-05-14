drop table if exists measurement;

create table measurement
(
    id INTEGER PRIMARY KEY,
    device_id INTEGER,
    timestamp TIMESTAMP,
    light    FLOAT,
    temp     FLOAT,
    water    FLOAT,
    humidity FLOAT
);
