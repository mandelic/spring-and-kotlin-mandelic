package com.example.project.carCheckUpSystem.carModel.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "carmodel")
class CarModel (
    @Id
    var id: UUID = UUID.randomUUID(),

    var manufacturer: String,

    var model: String
)