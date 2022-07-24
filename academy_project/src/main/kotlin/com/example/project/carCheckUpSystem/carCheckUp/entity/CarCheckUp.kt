package com.example.project.carCheckUpSystem.carCheckUp.entity

import com.example.project.carCheckUpSystem.car.entity.Car
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "carcheckup")
class CarCheckUp(
    @Id
    var id: UUID = UUID.randomUUID(),

    val performedAt: LocalDateTime,

    val workerName: String,

    val price: Long,

    @ManyToOne
    val car: Car
)