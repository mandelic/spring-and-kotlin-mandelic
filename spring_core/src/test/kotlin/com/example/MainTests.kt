package com.example

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

class MainTests @Autowired constructor(){
    lateinit var carCheckUpSystem: CarCheckUpSystem
    private val inMemoryCarRepository = mockk<InMemoryCarRepository>()
    private val inMemoryCarCheckUpRepository = mockk<InMemoryCarCheckUpRepository>()


    @BeforeEach
    fun setUp() {
        carCheckUpSystem= CarCheckUpSystem(inMemoryCarCheckUpRepository, inMemoryCarRepository)
    }


    @Test
    fun getCheckUps() {
        every { inMemoryCarCheckUpRepository.getAllCheckUps() } returns mutableMapOf(
            1L to CarCheckUp(1,LocalDateTime.of(2018,12,31,11,45), Car("Porsche", "Taycan", "4T1BE46K19U448615")),
            2L to CarCheckUp(2,LocalDateTime.of(2018, 2,17,12,15), Car("Audi", "A8", "1C4PJMDB6FW698828")),
            3L to CarCheckUp(3,LocalDateTime.of(2019, 1,3,13,0), Car("Audi", "A3", "5GAKRCKD6DJ199296")),
            4L to CarCheckUp(4,LocalDateTime.of(2021, 10,30,14,17), Car("Seat", "Ibiza", "2T3RFREV4EW154826")),
            5L to CarCheckUp(5,LocalDateTime.of(2020, 10,3,15,36), Car("Porsche", "Taycan", "4T1BE46K19U448615")),
            6L to CarCheckUp(6,LocalDateTime.of(2019, 5,28,16,28), Car("Audi", "A8", "1C4PJMDB6FW698828")),
            7L to CarCheckUp(7,LocalDateTime.of(2020, 7,3,17,40), Car("Audi", "A3", "5GAKRCKD6DJ199296")),
            8L to CarCheckUp(8,LocalDateTime.of(2022, 7,1,18,17), Car("Seat", "Ibiza", "2T3RFREV4EW154826")),
            9L to CarCheckUp(9,LocalDateTime.of(2021, 3,17,19,10), Car("Porsche", "Taycan", "4T1BE46K19U448615")),
            10L to CarCheckUp(10,LocalDateTime.of(2020, 9,21,20,30), Car("Audi", "A8", "1C4PJMDB6FW698828"))
        )

        val carCheckUps = carCheckUpSystem.getAll()

        assertThat(carCheckUps).isEqualTo(mutableMapOf(
            1L to CarCheckUp(1,LocalDateTime.of(2018,12,31,11,45), Car("Porsche", "Taycan", "4T1BE46K19U448615")),
            2L to CarCheckUp(2,LocalDateTime.of(2018, 2,17,12,15), Car("Audi", "A8", "1C4PJMDB6FW698828")),
            3L to CarCheckUp(3,LocalDateTime.of(2019, 1,3,13,0), Car("Audi", "A3", "5GAKRCKD6DJ199296")),
            4L to CarCheckUp(4,LocalDateTime.of(2021, 10,30,14,17), Car("Seat", "Ibiza", "2T3RFREV4EW154826")),
            5L to CarCheckUp(5,LocalDateTime.of(2020, 10,3,15,36), Car("Porsche", "Taycan", "4T1BE46K19U448615")),
            6L to CarCheckUp(6,LocalDateTime.of(2019, 5,28,16,28), Car("Audi", "A8", "1C4PJMDB6FW698828")),
            7L to CarCheckUp(7,LocalDateTime.of(2020, 7,3,17,40), Car("Audi", "A3", "5GAKRCKD6DJ199296")),
            8L to CarCheckUp(8,LocalDateTime.of(2022, 7,1,18,17), Car("Seat", "Ibiza", "2T3RFREV4EW154826")),
            9L to CarCheckUp(9,LocalDateTime.of(2021, 3,17,19,10), Car("Porsche", "Taycan", "4T1BE46K19U448615")),
            10L to CarCheckUp(10,LocalDateTime.of(2020, 9,21,20,30), Car("Audi", "A8", "1C4PJMDB6FW698828"))
        ))
        verify(exactly = 1) { inMemoryCarCheckUpRepository.getAllCheckUps() }
    }

    @Test
    fun isCheckUpNecessary() {
        val vin = "2"
        every {inMemoryCarRepository.findByVin(any())} throws CarNotFoundException(vin)
        assertThatThrownBy { carCheckUpSystem.addCheckUp(vin)}.isInstanceOf(CarNotFoundException::class.java).hasMessage("Car VIN $vin not found")
        verify(exactly = 1) { inMemoryCarRepository.findByVin(vin)}
    }

}