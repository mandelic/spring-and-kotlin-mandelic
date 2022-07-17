package com.example.project.carCheckUpSystem.carCheckUp

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class CarCheckUp(
    var id: Long = -1,
    val performedAt: LocalDateTime = LocalDateTime.now(),
    val workerName: String,
    val price: Long,
    val carId: Long
)