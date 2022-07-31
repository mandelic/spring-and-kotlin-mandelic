package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.controller.CarController
import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.entity.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.controller.CarCheckUpController
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
class CarDetailsResourceAssembler : RepresentationModelAssemblerSupport<CarDetails, CarDetailsResource>(
    CarCheckUpController::class.java, CarDetailsResource::class.java
) {
    override fun toModel(entity: CarDetails): CarDetailsResource {
        return createModelWithId(entity.car.id, entity).apply{
            add(
                linkTo<CarCheckUpController> {
                    getCarCheckUp(car.id)
                }.withRel("carCheckUps")
            )
        }
    }
    override fun instantiateModel(entity: CarDetails): CarDetailsResource {
        return CarDetailsResource(
            car = entity.car,
            checkupNecessary = entity.checkupNecessary
        )
    }

}


@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarDetailsResource(
    val car: Car,
    val checkupNecessary: Boolean
): RepresentationModel<CarDetailsResource>()