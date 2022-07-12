package com.example

class CarCheckUpNotFoundException(id: Long) : RuntimeException("com.example.Car check-up ID $id not found")