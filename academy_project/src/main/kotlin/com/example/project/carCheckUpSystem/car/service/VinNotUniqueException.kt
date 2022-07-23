package com.example.project.carCheckUpSystem.car.service

class VinNotUniqueException(vin: String): RuntimeException("Car VIN $vin already exists")