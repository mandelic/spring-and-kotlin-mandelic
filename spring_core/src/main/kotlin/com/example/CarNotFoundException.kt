package com.example

class CarNotFoundException(vin: String) : RuntimeException("com.example.Car VIN $vin not found")