CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY,
    dateAdded DATE,
    manufacturer VARCHAR,
    model VARCHAR,
    productionYear INT,
    vin VARCHAR UNIQUE
);

CREATE TABLE checkUps (
    id BIGSERIAL PRIMARY KEY,
    performedAt TIMESTAMP,
    workerName VARCHAR,
    price INT,
    carId BIGSERIAL REFERENCES cars(id)
);

