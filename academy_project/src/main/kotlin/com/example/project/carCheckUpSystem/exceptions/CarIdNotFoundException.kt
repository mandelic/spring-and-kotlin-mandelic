package com.example.project.carCheckUpSystem.exceptions

class CarIdNotFoundException(id: Long) : RuntimeException("Car ID $id not found")