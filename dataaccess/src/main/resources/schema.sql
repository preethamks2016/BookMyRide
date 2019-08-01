-- This file gets executed during the spring boot process and sets up the tables.

-- TIP:MODULE_SQL: Here is where all the schema creation of the DB happens
-- including creation of tables.
CREATE TABLE IF NOT EXISTS CarStatusTable (
    carId VARCHAR(100) NOT NULL PRIMARY KEY,
    carType INTEGER NOT NULL,
    carAvailability INTEGER NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE);

CREATE TABLE IF NOT EXISTS CarDetailsTable (
    carId VARCHAR(100) NOT NULL PRIMARY KEY,
    carType INTEGER NOT NULL,
    driverId VARCHAR(100) NOT NULL,
    carModel VARCHAR(100),
    carLicense VARCHAR(100));

CREATE TABLE IF NOT EXISTS DriverDetailsTable (
    driverId VARCHAR(100) NOT NULL PRIMARY KEY,
    carId VARCHAR(100) NOT NULL,
    name VARCHAR(100),
    phone VARCHAR(100));

CREATE TABLE IF NOT EXISTS TripTable (
    tripId VARCHAR(100) NOT NULL PRIMARY KEY,
    carId VARCHAR(100) NOT NULL,
    driverId VARCHAR(100) NOT NULL,
    sourceLocationLatitude DOUBLE,
    sourceLocationLongitude DOUBLE,
    destinationLocationLatitude DOUBLE,
    destinationLocationLongitude DOUBLE,
    startTimeInEpochs LONG,
    endTimeInEpochs LONG,
    tripPrice FLOAT,
    tripStatus INTEGER,
    paymentMode INTEGER);
