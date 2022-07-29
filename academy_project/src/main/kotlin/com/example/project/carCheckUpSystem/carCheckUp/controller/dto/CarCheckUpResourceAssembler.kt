package com.example.project.carCheckUpSystem.carCheckUp.controller.dto

import com.example.project.carCheckUpSystem.carCheckUp.controller.CarCheckUpController
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*


@Component
class CarCheckUpResourceAssembler : RepresentationModelAssemblerSupport<CarCheckUp, CarCheckUpResource>(
    CarCheckUpController::class.java, CarCheckUpResource::class.java
) {
    override fun toModel(entity: CarCheckUp): CarCheckUpResource {
        return createModelWithId(entity.id, entity).apply{
            add(
                linkTo<CarCheckUpController> {
                    getCarCheckUp(carId)
                }.withRel("carCheckUp")
            )
        }
    }
    override fun instantiateModel(entity: CarCheckUp): CarCheckUpResource {
        return CarCheckUpResource(
            id = entity.id,
            performedAt = entity.performedAt,
            workerName = entity.workerName,
            price = entity.price,
            carId = entity.car.id
        )
    }

}

@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarCheckUpResource(
    val id: UUID,
    val performedAt: LocalDateTime,
    val workerName: String,
    val price: Long,
    val carId: UUID
): RepresentationModel<CarCheckUpResource>()