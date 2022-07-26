package com.example.project.carCheckUpSystem.car.service.exception

class CarVinNotFoundException(vin: String) : RuntimeException("Car VIN $vin not found")