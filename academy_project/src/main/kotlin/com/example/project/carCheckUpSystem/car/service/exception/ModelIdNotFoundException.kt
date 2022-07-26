package com.example.project.carCheckUpSystem.car.service.exception

import java.util.*

class ModelIdNotFoundException(modelId: UUID) : RuntimeException("Model ID $modelId not found")

