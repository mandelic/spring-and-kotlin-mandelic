package com.example

class CarNotFoundException(vin: String) : RuntimeException("Car VIN $vin not found")