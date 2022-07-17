package com.example.project.carCheckUpSystem.exceptions

class CarNotFoundException(id: Long) : RuntimeException("Car ID $id not found")