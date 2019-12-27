package com.origin.risk.domain.customer

import arrow.core.None
import arrow.core.Option
import com.origin.risk.domain.assets.House
import com.origin.risk.domain.assets.Vehicle

data class Customer(
    val age: Int = 0,
    val dependents: Int = 0,
    val income: Int = 0,
    val maritalStatus: MaritalStatus = MaritalStatus.SINGLE,
    val house: Option<House> = None,
    val vehicle: Option<Vehicle> = None
)

enum class MaritalStatus {
    SINGLE,
    MARRIED;
}