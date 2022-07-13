package com.example

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.FileSystemResource

fun main() {
    val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(ApplicationConfig::class.java)
    val carCheckUpSystem = applicationContext.getBean(CarCheckUpSystem::class.java)
    carCheckUpSystem.getCheckUps("2T3RFREV4EW154826")?.forEach { println("${it.performedAt} ${it.car.vin}")  }
    println(carCheckUpSystem.countCheckUps("Seat"))
    carCheckUpSystem.addCheckUp("2T3RFREV4EW154826")
    carCheckUpSystem.getCheckUps("2T3RFREV4EW154826")?.forEach { println("${it.performedAt} ${it.car.vin}")  }
    println(if (carCheckUpSystem.isCheckUpNecessary("2T3RFREV4EW154826") ) "Check up is necessary" else "Check up is not necessary")
    println(if (carCheckUpSystem.isCheckUpNecessary("5GAKRCKD6DJ199296") ) "Check up is necessary" else "Check up is not necessary")
}

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class ApplicationConfig {
    @Bean
    fun fileSystemResource(): FileSystemResource = FileSystemResource("C:/")

}