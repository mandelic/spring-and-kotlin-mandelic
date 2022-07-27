CREATE TABLE carModel (
      id UUID PRIMARY KEY,
      manufacturer TEXT,
      model TEXT
);
ALTER TABLE car
ADD COLUMN car_model_id UUID CONSTRAINT carModel_fk REFERENCES carModel(id);


