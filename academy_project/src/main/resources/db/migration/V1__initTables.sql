CREATE TABLE car (
    id UUID PRIMARY KEY,
    date_added DATE,
    manufacturer TEXT,
    model TEXT,
    production_year INT,
    vin TEXT UNIQUE
);
CREATE TABLE carCheckUp (
    id UUID PRIMARY KEY,
    performed_at TIMESTAMP,
    worker_name TEXT,
    price INT,
    car_id UUID CONSTRAINT car_fk REFERENCES car(id)
);

