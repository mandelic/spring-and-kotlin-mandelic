package com.example.project.carCheckUpSystem.carModel.service

import com.fasterxml.jackson.annotation.JsonProperty

data class CarModelResponse(
    @JsonProperty("cars") val cars: List<CarManufacturers>
)

data class CarManufacturers(
    @JsonProperty("manufacturer") val manufacturer: String,
    @JsonProperty("models") val models: List<String>
)