package com.example.project.carcheckup.repository

import com.example.project.carcheckup.entity.Car
import com.example.project.carcheckup.entity.CarCheckUp
import com.example.project.carcheckup.exceptions.CarNotFoundException
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year

@Repository
class InMemoryCarRepository : CarRepository{

    private val carMap = mutableMapOf<Long, Car>()
    init {
        carMap[1] = Car(1, LocalDate.of(2018, 12, 5),"Porsche", "Taycan", 2012,"4T1BE46K19U448615",
            mutableListOf<CarCheckUp>(
            CarCheckUp(1, LocalDateTime.of(2018,12,31,11,45), "John", 1000,1),
            CarCheckUp(5, LocalDateTime.of(2020, 10,3,15,36), "Amanda",550,1),
            CarCheckUp(9, LocalDateTime.of(2021, 3,17,19,10), "John",2000,1)
        ))
        carMap[2] = Car(2,LocalDate.of(2019, 5, 12),"Audi", "A8", 2015,"1C4PJMDB6FW698828",
            mutableListOf<CarCheckUp>(
                CarCheckUp(2, LocalDateTime.of(2018, 2,17,12,15), "Amanda",1500,2),
                CarCheckUp(6, LocalDateTime.of(2019, 5,28,16,28), "Amanda",480,2),
                CarCheckUp(10, LocalDateTime.of(2020, 9,21,20,30), "Mark",1000,2)
            ))
        carMap[3] = Car(3, LocalDate.of(2020, 9, 1),"Audi", "A3", 2008, "5GAKRCKD6DJ199296",
            mutableListOf<CarCheckUp>(
                CarCheckUp(3, LocalDateTime.of(2019, 1,3,13,0),"John", 1750,3),
                CarCheckUp(7, LocalDateTime.of(2020, 7,3,17,40), "Mark",1300,3)
            ))
        carMap[4] = Car(4,LocalDate.of(2017, 2,2), "Seat", "Ibiza", 2003,"2T3RFREV4EW154826",
            mutableListOf<CarCheckUp>(
                CarCheckUp(4, LocalDateTime.of(2021, 10,30,14,17), "Coleen",820,4),
                CarCheckUp(8, LocalDateTime.of(2022, 7,1,18,17), "John",1000,4)
            ))
    }

    override fun insertData(dateAdded: LocalDate, manufacturer: String, model: String, productionYear: Int, vin: String): Long {
        val id = (carMap.keys.maxOrNull() ?: 0) + 1
        carMap[id] = Car(id = id, dateAdded = dateAdded, manufacturer = manufacturer, model = model, productionYear = productionYear, vin = vin, carCheckUpList = mutableListOf<CarCheckUp>())
        return id
    }

    override fun findById(id: Long): Car {
        return carMap[id] ?: throw CarNotFoundException(id)
    }

    override fun deleteById(id: Long): Car {
        return carMap.remove(id) ?: throw CarNotFoundException(id)
    }

    override fun findAll(): List<Car> = carMap.toList().map{it.second}

    override fun insertCar(car: Car): Car{
        val id = (carMap.keys.maxOrNull() ?: 0) + 1
        carMap[id] = car
        return car
    }

    override fun addCarCheckUp(id: Long, carCheckUp: CarCheckUp): Boolean? = carMap[id]?.carCheckUpList?.add(carCheckUp)

}