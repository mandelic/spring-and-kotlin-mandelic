package com.example
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.io.FileOutputStream
import java.time.LocalDateTime

@Repository
class InFileCarCheckUpRepository(
    @Value("\${Resource}")
    private val carCheckUpsFileResource: Resource,

) : com.example.CarCheckUpRepository {
    init {
        if (carCheckUpsFileResource.exists().not()) {
            carCheckUpsFileResource.file.createNewFile()
        }
    }
    override fun insert(performedAt: LocalDateTime, car: com.example.Car): Long {
        val file = carCheckUpsFileResource.file
        val id = (file.readLines()
            .filter { it.isNotEmpty() }.maxOfOrNull { line ->
                line.split(",").first().toLong() } ?: 0) + 1
        file.appendText("$id,${car.vin},${car.manufacturer},${car.model},$performedAt\n")
        return id
    }
    override fun findById(id: Long): com.example.CarCheckUp {
        return carCheckUpsFileResource.file.readLines()
            .filter { it.isNotEmpty() }
            .find { line -> line.split(",").first().toLong() == id }
            ?.convertToCarCheckUp()
            ?: throw com.example.CarCheckUpNotFoundException(id)
    }
    override fun deleteById(id: Long): com.example.CarCheckUp {
        val checkUpLines = carCheckUpsFileResource.file.readLines()
        var lineToDelete: String? = null
        FileOutputStream(carCheckUpsFileResource.file)
            .writer()
            .use { fileOutputWriter ->
                checkUpLines.forEach { line ->
                    if (line.split(",").first().toLong() == id) {
                        lineToDelete = line
                    } else {
                        fileOutputWriter.appendLine(line)
                    }
                }
            }
        return lineToDelete?.convertToCarCheckUp() ?: throw
        com.example.CarCheckUpNotFoundException(id)
    }

    override fun getCheckUps(): Map<Long, com.example.CarCheckUp> {
        return carCheckUpsFileResource.file.readLines()
            .map { line -> line.convertToCarCheckUp() }
            .associateBy { it.id }
    }

    private fun String.convertToCarCheckUp(): com.example.CarCheckUp {
        val tokens = split(",")
        return com.example.CarCheckUp(
            id = tokens[0].toLong(),
            performedAt = LocalDateTime.parse(tokens[4]),
            car = com.example.Car(
                vin = tokens[1],
                manufacturer = tokens[2],
                model = tokens[3]
            )
        )
    }
}