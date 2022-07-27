package com.example.project.carCheckUpSystem.car.entity

import com.example.project.carCheckUpSystem.carModel.controller.dto.CarModelDTO
import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "car")
class Car(

    @Id
    var id: UUID = UUID.randomUUID(),

    val dateAdded: LocalDate,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    val carModel: CarModel,

    val productionYear: Int,

    val vin: String
)