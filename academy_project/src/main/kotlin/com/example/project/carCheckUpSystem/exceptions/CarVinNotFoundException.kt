package com.example.project.carCheckUpSystem.exceptions

class CarVinNotFoundException(vin: String) : RuntimeException("Car VIN $vin not found")