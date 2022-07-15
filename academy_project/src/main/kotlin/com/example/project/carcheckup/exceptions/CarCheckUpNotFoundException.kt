package com.example.project.carcheckup.exceptions

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")