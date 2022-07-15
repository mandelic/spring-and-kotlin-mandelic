package com.example.project.carcheckup.exceptions

class CarNotFoundException(id: Long) : RuntimeException("Car ID $id not found")