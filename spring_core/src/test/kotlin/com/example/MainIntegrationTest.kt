package com.example

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApplicationConfig::class])
@TestPropertySource(locations=["classpath:test2.properties"])
class MainIntegrationTest @Autowired constructor(private var applicationContext: ApplicationContext,
                                                 private val carCheckUpSystem: CarCheckUpSystem,
                                                private val dataSource: DataSource) {
    @Test
    fun verifyCarCheckUpSystemBean() {
        assertThat(applicationContext.getBean(carCheckUpSystem::class.java)).isNotNull
    }

    @Test
    fun testProperty2() {
        val dbnameTest = dataSource.dbName
        val usernameTest = dataSource.username
        val passwordTest = dataSource.password
        assertThat(dbnameTest).isEqualTo("hola")
        assertThat(usernameTest).isEqualTo("hola user")
        assertThat(passwordTest).isEqualTo("hola pass")
    }


    @Test
    fun isCheckUpNecessary(){
        val checkUpNecessary = carCheckUpSystem.isCheckUpNecessary("2T3RFREV4EW154826")
        assertThat(checkUpNecessary).isFalse
        val checkUpNotNecessary = carCheckUpSystem.isCheckUpNecessary("5GAKRCKD6DJ199296")
        assertThat(checkUpNotNecessary).isTrue
    }

    @Test
    fun addCheckUp() {
        val addedCheckUp = carCheckUpSystem.addCheckUp("5GAKRCKD6DJ199296")
        val checkCheckUp = carCheckUpSystem.getCheckUps("5GAKRCKD6DJ199296")
        assertThat(addedCheckUp).isIn(checkCheckUp)
    }

    @Test
    fun countCheckUps() {
        var countCheckUps = carCheckUpSystem.countCheckUps("Seat")
        assertThat(countCheckUps).isEqualTo(2)
        countCheckUps = carCheckUpSystem.countCheckUps("Porsche")
        assertThat(countCheckUps).isEqualTo(3)
    }

}
