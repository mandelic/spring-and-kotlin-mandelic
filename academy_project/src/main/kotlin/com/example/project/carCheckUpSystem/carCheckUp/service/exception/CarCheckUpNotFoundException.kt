package com.example.project.carCheckUpSystem.carCheckUp.service.exception

import java.util.*

class CarCheckUpNotFoundException(id: UUID) : RuntimeException("Car check-up ID $id not found")