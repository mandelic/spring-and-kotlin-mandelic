package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.controller.CarController
import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class CarResourceAssembler : RepresentationModelAssemblerSupport<Car, CarResource>(
    CarController::class.java, CarResource::class.java
    ) {
    override fun toModel(entity: Car): CarResource {
        return createModelWithId(entity.id, entity).apply{
            add(
                linkTo<CarController> {
                    fetchCar(id)
                }.withRel("car")
            )
        }
    }
    override fun instantiateModel(entity: Car): CarResource {
        return CarResource(
            id = entity.id,
            dateAdded = entity.dateAdded,
            productionYear = entity.productionYear,
            vin = entity.vin,
            carModelId = entity.carModel.id
        )
    }

}


@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarResource(
    val id: UUID,
    val dateAdded: LocalDate,
    val productionYear: Int,
    val vin: String,
    val carModelId: UUID
    ): RepresentationModel<CarResource>()