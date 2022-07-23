package com.example.project.carCheckUpSystem.car.service

import java.util.*

class CarIdNotFoundException(id: UUID) : RuntimeException("Car ID $id not found")