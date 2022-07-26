package com.example.project.carCheckUpSystem.carModel.service

import com.example.project.carCheckUpSystem.carModel.controller.dto.CarModelDTO

interface CarModelService {
    fun getAllTmp(): List<CarManufacturers>
    fun getAllDb(): List<CarModelDTO>
}