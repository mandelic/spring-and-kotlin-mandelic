package com.example.project.carCheckUpSystem.exceptions

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")