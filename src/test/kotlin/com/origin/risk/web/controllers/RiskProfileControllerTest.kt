package com.origin.risk.web.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.origin.risk.domain.assets.OwnershipStatus
import com.origin.risk.web.messaging.HouseRequest
import com.origin.risk.web.messaging.RiskProfileRequest
import com.origin.risk.web.messaging.RiskProfileResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class RiskProfileControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `age is an required integer equal or greater than 0`() {
        val request = RiskProfileRequest(
            age = -1
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `the number of dependents is an required integer equal or greater than 0)`() {
        val request = RiskProfileRequest(
            dependents = -1
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `income is an required integer equal or greater than 0`() {
        val request = RiskProfileRequest(
            income = -1
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `risk questions is an array with 3 booleans`() {
        val request = RiskProfileRequest(
            riskQuestions = arrayOf(1,1,3)
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)

        val request2 = RiskProfileRequest(
            riskQuestions = arrayOf(1)
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request2))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)

        val request3 = RiskProfileRequest(
            riskQuestions = arrayOf(1,1,1,1,1)
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request3))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `users can have 0 or 1 house, when they do, it has just one attribute ownership_status, which can be "owned" or "mortgaged"`() {
        val request = RiskProfileRequest(
            house = null,
            riskQuestions = arrayOf(1,1,1)
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        val request2 = "{\"age\":0,\"dependents\":0,\"income\":0,\"maritalStatus\":\"SINGLE\",\"house\":{\"ownership_status\":\"invalid_value\"},\"vehicle\":null,\"riskQuestions\":[1,1,1]}"

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request2))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `users can have 0 or 1 vehicle, when they do, it has just one attribute a positive integer corresponding to the year it was manufactured`() {
        val request = RiskProfileRequest(
            vehicles = null,
            riskQuestions = arrayOf(1,1,1)
        )

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        val request2 = "{\"age\":0,\"dependents\":0,\"income\":0,\"maritalStatus\":\"SINGLE\",\"house\":null,\"vehicle\":{\"year\":-1},\"riskQuestions\":[1,1,1]}"

        mockMvc.perform(post("/insurance/risk-profile")
            .content(objectMapper.writeValueAsString(request2))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `When the users house is Rented, it receives Renters recommendation`() {
        val request = RiskProfileRequest(
                house = HouseRequest(OwnershipStatus.RENTED, false),
                riskQuestions = arrayOf(1,1,1)
        )

        val mvcResult = mockMvc.perform(post("/insurance/risk-profile")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val responseObject = objectMapper.readValue<RiskProfileResponse>(mvcResult.response.contentAsString)
        assert(responseObject.home == null)
        assert(responseObject.rent != null)
    }

    @Test
    fun `When the users house is Owned, it receives Home recommendation`() {
        val request = RiskProfileRequest(
                house = HouseRequest(OwnershipStatus.OWNED, false),
                riskQuestions = arrayOf(1,1,1)
        )

        val mvcResult = mockMvc.perform(post("/insurance/risk-profile")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val responseObject = objectMapper.readValue<RiskProfileResponse>(mvcResult.response.contentAsString)
        assert(responseObject.home != null)
        assert(responseObject.rent == null)
    }
}
