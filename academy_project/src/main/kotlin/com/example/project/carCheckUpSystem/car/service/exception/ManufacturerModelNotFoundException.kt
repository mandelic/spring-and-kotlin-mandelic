package com.example.project.carCheckUpSystem.car.service.exception

class ManufacturerModelNotFoundException(manufacturer: String, model: String): RuntimeException("Model ${model} of Manufacturer ${manufacturer} not found")