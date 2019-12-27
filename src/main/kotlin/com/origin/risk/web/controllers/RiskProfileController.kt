package com.origin.risk.web.controllers

import com.origin.risk.domain.insurance.RiskProfileService
import com.origin.risk.web.messaging.RiskProfileRequest
import com.origin.risk.web.messaging.RiskProfileResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class RiskProfileController {
    @PostMapping("/insurance/risk-profile")
    fun riskProfile(@Valid @RequestBody request: RiskProfileRequest): ResponseEntity<RiskProfileResponse> {
        val customer = request.mapToDomain()
        val riskQuestions = request.riskQuestions
        val profiles = RiskProfileService.riskProfile(customer, riskQuestions!!)

        return ResponseEntity(
            RiskProfileResponse.mapFromDomain(profiles),
            HttpStatus.OK
        )
    }
}