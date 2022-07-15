package com.example.project.carcheckup.entity

import java.time.LocalDateTime

data class CarCheckUp(
    val id: Long,
    val performedAt: LocalDateTime,
    val workerName: String,
    val price: Long,
    val carId: Long
)