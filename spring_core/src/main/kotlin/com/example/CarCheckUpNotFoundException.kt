package com.example

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")