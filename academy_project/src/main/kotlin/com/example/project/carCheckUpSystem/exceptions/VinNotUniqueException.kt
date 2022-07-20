package com.example.project.carCheckUpSystem.exceptions

class VinNotUniqueException(vin: String): RuntimeException("Car VIN $vin already exists")