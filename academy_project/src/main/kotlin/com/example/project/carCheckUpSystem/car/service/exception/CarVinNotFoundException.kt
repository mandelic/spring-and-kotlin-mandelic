package com.example.project.carCheckUpSystem.car.service

class CarVinNotFoundException(vin: String) : RuntimeException("Car VIN $vin not found")