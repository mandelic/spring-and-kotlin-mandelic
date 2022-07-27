package com.example.project.config

import com.example.project.carCheckUpSystem.carModel.service.RestTemplateCarModelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulerConfiguration (
    @Autowired
    private val carModelService: RestTemplateCarModelService
        ) {

    @Scheduled(cron = "@daily")
    fun updateCarModelTable() = carModelService.updateDatabase()
}