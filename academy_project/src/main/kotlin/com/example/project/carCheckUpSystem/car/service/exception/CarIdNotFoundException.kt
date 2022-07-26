package com.example.project.carCheckUpSystem.car.service.exception

import java.util.*

class CarIdNotFoundException(id: UUID) : RuntimeException("Car ID $id not found")