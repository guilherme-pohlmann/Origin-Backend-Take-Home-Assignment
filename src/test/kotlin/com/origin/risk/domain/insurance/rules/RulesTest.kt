package com.origin.risk.domain.insurance.rules

import arrow.core.toOption
import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.customer.MaritalStatus
import com.origin.risk.domain.assets.House
import com.origin.risk.domain.assets.OwnershipStatus
import com.origin.risk.domain.assets.Vehicle
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Score
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class RulesTest {

    @Nested
    inner class DependentsRuleTest {
        @Test
        fun `if the user has dependents, add 1 risk point`() {
            val customer = Customer(dependents = 1)
            val profile = RiskProfile(score = Score(1))

            assert(DependentsRule.apply(customer, profile).score.value == 2)
        }
    }

    @Nested
    inner class DisabilityInsuranceMarriedRuleTest {
        @Test
        fun `if the user is married, remove 1 risk point`() {
            val customer = Customer(maritalStatus = MaritalStatus.MARRIED)
            val profile = RiskProfile(score = Score(1))

            assert(DisabilityInsuranceMarriedRule.apply(customer, profile).score.value == 0)
        }
    }

    @Nested
    inner class HighIncomeRuleTest {
        @Test
        fun `if her income is above $200k, deduct 1 risk point `() {
            val customer = Customer(income = 201000)
            val profile = RiskProfile(score = Score(1))

            assert(HighIncomeRule.apply(customer, profile).score.value == 0)
        }
    }

    @Nested
    inner class HouseRuleTest {
        @Test
        fun `if the user doesn’t have houses, she is ineligible`() {
            val customer = Customer()
            val profile = RiskProfile(score = Score(1))

            assert(!HouseRule.apply(customer, profile).eligible)
        }
    }

    @Nested
    inner class IncomeRuleTest {
        @Test
        fun `if the user doesn’t have income, she is ineligible`() {
            val customer = Customer()
            val profile = RiskProfile(score = Score(1))

            assert(!IncomeRule.apply(customer, profile).eligible)
        }
    }

    @Nested
    inner class LifeInsuranceMarriedRuleTest {
        @Test
        fun `if the user is married, add 1 risk point `() {
            val customer = Customer(maritalStatus = MaritalStatus.MARRIED)
            val profile = RiskProfile(score = Score(1))

            assert(LifeInsuranceMarriedRule.apply(customer, profile).score.value == 2)
        }
    }

    @Nested
    inner class LowAgeRuleTest {
        @Test
        fun `if the user is under 30 years old, deduct 2 risk points`() {
            val customer = Customer(age = 29)
            val profile = RiskProfile(score = Score(4))

            assert(LowAgeRule.apply(customer, profile).score.value == 2)
        }

        @Test
        fun `if she is between 30 and 40 years old, deduct 1`() {
            val customer = Customer(age = 34)
            val profile = RiskProfile(score = Score(4))

            assert(LowAgeRule.apply(customer, profile).score.value == 3)
        }
    }

    @Nested
    inner class MortgagedHouseRuleTest {
        @Test
        fun `if the user's house is mortgaged, add 1 risk point`() {
            val customer = Customer(house = House(OwnershipStatus.OWNED, true).toOption())
            val profile = RiskProfile(score = Score(1))

            assert(MortgagedHouseRule.apply(customer, profile).score.value == 2)
        }
    }

    @Nested
    inner class HighAgeRuleTest {
        @Test
        fun `if the user is over 60 years old, she is ineligible`() {
            val customer = Customer(age = 61)
            val profile = RiskProfile(score = Score(1))

            assert(!HighAgeRule.apply(customer, profile).eligible)
        }
    }

    @Nested
    inner class VehicleAgeRuleTest {
        @Test
        fun `if the user's vehicle was produced in the last 5 years, add 1 risk point`() {
            val customer = Customer(vehicles = listOf(Vehicle(0,LocalDate.now().year - 2)))
            val profile = RiskProfile(score = Score(1))

            assert(VehicleAgeRule(customer.vehicles.first()).apply(customer, profile).score.value == 2)
        }
    }

    @Nested
    inner class VehicleRuleTest {
        @Test
        fun `if the user doesn’t have vehicles she is ineligible`() {
            val customer = Customer()
            val profile = RiskProfile(score = Score(1))

            assert(!VehicleRule.apply(customer, profile).eligible)
        }
    }
}