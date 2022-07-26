package com.example.project.carCheckUpSystem.carCheckUp.service.exception

import java.util.*

class CarIdNotFoundException(id: UUID) : RuntimeException("Car ID $id not found")