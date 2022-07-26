package com.example.project.carCheckUpSystem.car.service.exception

class VinNotUniqueException(vin: String): RuntimeException("Car VIN $vin already exists")