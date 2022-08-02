ALTER TABLE carcheckup
drop CONSTRAINT car_fk;

ALTER TABLE carcheckup
ADD CONSTRAINT car_FK
    FOREIGN KEY (car_id)
    REFERENCES car(id)
    ON DELETE CASCADE;