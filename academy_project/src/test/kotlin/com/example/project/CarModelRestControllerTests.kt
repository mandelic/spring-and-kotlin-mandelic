package com.example.project

import com.example.project.carCheckUpSystem.carModel.service.CarModelResponse
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import org.mockserver.springtest.MockServerTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import com.fasterxml.jackson.databind.ObjectMapper

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class CarModelRestControllerTests @Autowired constructor(
    private val mockMvc: MockMvc,
){
    lateinit var mockServerClient: MockServerClient

    @Test
    fun test() {
        mockServerClient .`when`(
            HttpRequest.request()
        ).respond(
            HttpResponse.response().withStatusCode(200).withContentType(MediaType.APPLICATION_JSON)
                .withBody("""{"cars":[{"manufacturer":"Porsche","models":["911 Turbo","Cayenne","Panamera"]},{"manufacturer":"Citroen","models":["C3","C4","C5"]},{"manufacturer":"Volkswagen","models":["Polo"]},{"manufacturer":"Hyundai","models":["i30","i20","i35","i10"]}],"Car":"1"}""")
        )
        mockMvc.get("/models/get-from-template")
            .andExpect {
                status { isOk() }
                content { """[{"manufacturer":"Porsche","models":["911 Turbo","Cayenne","Panamera"]},{"manufacturer":"Citroen","models":["C3","C4","C5"]},{"manufacturer":"Volkswagen","models":["Polo"]},{"manufacturer":"Hyundai","models":["i30","i20","i35","i10"]}]"""}
            }
    }
}