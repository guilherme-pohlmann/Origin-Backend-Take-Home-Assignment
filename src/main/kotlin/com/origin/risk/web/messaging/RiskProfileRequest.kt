package com.origin.risk.web.messaging

import arrow.core.toOption
import com.origin.risk.domain.assets.House
import com.origin.risk.domain.assets.OwnershipStatus
import com.origin.risk.domain.assets.Vehicle
import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.customer.MaritalStatus
import com.origin.risk.web.support.ValidRiskQuestions
import javax.validation.constraints.*

data class RiskProfileRequest(
    @field:PositiveOrZero
    val age: Int = 0,

    @field:PositiveOrZero
    val dependents: Int = 0,

    @field:PositiveOrZero
    val income: Int = 0,

    @field:NotNull
    val maritalStatus: MaritalStatus = MaritalStatus.SINGLE,

    val house: HouseRequest? = null,
    val vehicles: List<VehicleRequest>? = null,

    @field:ValidRiskQuestions
    @field:Size(min = 3, max = 3)
    @field:NotNull
    val riskQuestions: Array<Int>? = emptyArray()
) {
    fun mapToDomain() = Customer(
        age = age,
        dependents = dependents,
        income = income,
        maritalStatus = maritalStatus,
        house = (house?.mapToDomain()).toOption(),
        vehicles = vehicles?.map { it.mapToDomain() } ?: emptyList()
    )
}

data class HouseRequest(
    @field:NotNull
    val ownershipStatus: OwnershipStatus
) {
    fun mapToDomain() = House(ownershipStatus = ownershipStatus)
}

data class VehicleRequest
(
   @field:PositiveOrZero
   val key: Int,

    @field:PositiveOrZero
    val year: Int
) {
    fun mapToDomain() = Vehicle(key = key, year = year)
}