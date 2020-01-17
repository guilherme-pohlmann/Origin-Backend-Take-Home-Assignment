package com.origin.risk.domain.insurance

import arrow.core.toOption
import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.customer.MaritalStatus
import com.origin.risk.domain.assets.House
import com.origin.risk.domain.assets.OwnershipStatus
import com.origin.risk.domain.assets.Vehicle
import org.junit.jupiter.api.Test
import java.time.LocalDate

class RiskProfileServiceTest {
    @Test
    fun `if the user doesn’t have income, vehicles or houses, she is ineligible for disability, auto, and home insurance, respectively`() {
        val customer = Customer()
        val profiles = RiskProfileService.riskProfile(customer, emptyArray())

        assert(!profiles.first { it.name == "auto" }.eligible)
        assert(!profiles.first { it.name == "disability" }.eligible)
        assert(!profiles.first { it.name == "home" }.eligible)
    }

    @Test
    fun `if the user is over 60 years old, she is ineligible for disability and life insurance`() {
        val customer = Customer(age = 61)
        val profiles = RiskProfileService.riskProfile(customer, emptyArray())

        assert(!profiles.first { it.name == "life" }.eligible)
        assert(!profiles.first { it.name == "disability" }.eligible)
    }

    @Test
    fun `if the user is under 30 years old, deduct 2 risk points from all lines of insurance`() {
        val customer = Customer(age = 28, vehicles = listOf(Vehicle(0, LocalDate.now().year - 6)))
        val profiles = RiskProfileService.riskProfile(customer, arrayOf(1,1,1))

        assert(profiles.all { it.score.value == 1})
    }

    @Test
    fun `If she is between 30 and 40 years old, deduct 1 risk points from all lines of insurance`() {
        val customerOlder = Customer(age = 35, vehicles = listOf(Vehicle(0, LocalDate.now().year - 6)))
        val profilesOlderCustomer = RiskProfileService.riskProfile(customerOlder, arrayOf(1,1,1))

        assert(profilesOlderCustomer.all { it.score.value == 2})
    }

    @Test
    fun `if her income is above $200k, deduct 1 risk point from all lines of insurance`() {
        val customer = Customer(
            income = 300000,
            age = 50,
            vehicles = listOf(Vehicle(0, LocalDate.now().year - 6))
        )
        val profiles = RiskProfileService.riskProfile(customer, arrayOf(1,1,1))

        assert(profiles.all { it.score.value == 2 })
    }

    @Test
    fun `if the user's house is mortgaged, add 1 risk point to her home score and add 1 risk point to her disability score`() {
        val customer = Customer(
            house = House(OwnershipStatus.MORTGAGED).toOption(),
            age = 50
        )
        val profiles = RiskProfileService.riskProfile(customer, arrayOf(1,1,1))

        assert(profiles.first { it.name == "home" }.score.value == 4)
        assert(profiles.first { it.name == "disability" }.score.value == 4)
        assert(profiles.filter { it.name != "home" && it.name != "disability" }.all { it.score.value == 3 })
    }

    @Test
    fun `if the user has dependents, add 1 risk point to both the disability and life scores`() {
        val customer = Customer(
            dependents = 1,
            age = 50
        )
        val profiles = RiskProfileService.riskProfile(customer, arrayOf(1,1,1))

        assert(profiles.first { it.name == "life" }.score.value == 4)
        assert(profiles.first { it.name == "disability" }.score.value == 4)
        assert(profiles.filter { it.name != "life" && it.name != "disability" }.all { it.score.value == 3 })
    }

    @Test
    fun `if the user is married, add 1 risk point to the life score and remove 1 risk point from disability`() {
        val customer = Customer(
            maritalStatus = MaritalStatus.MARRIED,
            age = 50
        )
        val profiles = RiskProfileService.riskProfile(customer, arrayOf(1,1,1))

        assert(profiles.first { it.name == "life" }.score.value == 4)
        assert(profiles.first { it.name == "disability" }.score.value == 2)
        assert(profiles.filter { it.name != "life" && it.name != "disability" }.all { it.score.value == 3 })
    }

    @Test
    fun `if the user's vehicle was produced in the last 5 years, add 1 risk point to that vehicle’s score`() {
        val customer = Customer(
            vehicles = listOf(Vehicle(0, LocalDate.now().year - 2)),
            age = 50
        )
        val profiles = RiskProfileService.riskProfile(customer, arrayOf(1,1,1))

        assert(profiles.first { it.name == "auto" }.score.value == 4)
        assert(profiles.filter { it.name != "auto" }.all { it.score.value == 3 })
    }
}