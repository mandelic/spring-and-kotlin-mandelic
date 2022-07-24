package com.example.project.carCheckUpSystem.car.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "car")
class Car(

    @Id
    var id: UUID = UUID.randomUUID(),

    val dateAdded: LocalDate,

    val manufacturer: String,

    val model: String,

    val productionYear: Int,

    val vin: String
)