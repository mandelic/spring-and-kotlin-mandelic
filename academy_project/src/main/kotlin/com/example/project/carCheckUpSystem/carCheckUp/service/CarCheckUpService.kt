package com.example.project.carCheckUpSystem.carCheckUp.service

import com.example.project.carCheckUpSystem.car.repository.CarRepository
import com.example.project.carCheckUpSystem.car.service.exception.CarIdNotFoundException
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.AddCarCheckUpDTO
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.CarCheckUpDTO
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import com.example.project.carCheckUpSystem.carCheckUp.repository.CarCheckUpRepository
import com.example.project.carCheckUpSystem.carCheckUp.service.exception.CarCheckUpNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarCheckUpService (val carCheckUpRepository: CarCheckUpRepository,
                         val carRepository: CarRepository
) {
    fun addCarCheckUp(dto: AddCarCheckUpDTO): CarCheckUpDTO{
        val carCheckUp = dto.toCarCheckUp { carId ->
            carRepository.findById(carId).orElseThrow { CarIdNotFoundException(carId) }
        }
        return CarCheckUpDTO(
            carCheckUpRepository.save(carCheckUp)
        )
    }
    fun getAllCheckUps() = carCheckUpRepository.findAll()

    fun getAllCheckUps(pageable: Pageable) = carCheckUpRepository.findAll( pageable)

    fun getCheckUps(id: UUID) = carCheckUpRepository.findByCarId(id)
    fun getPagedCheckUps(id: UUID, pageable: Pageable) = carCheckUpRepository.findAllByCarId(id, pageable)
    fun getCheckUpById(id: UUID): CarCheckUp {
        val carCheckUp = carCheckUpRepository.findById(id)
        return carCheckUp.orElseThrow {CarCheckUpNotFoundException(id)}
    }
    fun delete(id: UUID) {
        if (!carCheckUpRepository.existsById(id)) throw CarCheckUpNotFoundException(id)
        carCheckUpRepository.deleteById(id)
    }
}