package com.example.project.carCheckUpSystem.carCheckUp.service.exception

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")