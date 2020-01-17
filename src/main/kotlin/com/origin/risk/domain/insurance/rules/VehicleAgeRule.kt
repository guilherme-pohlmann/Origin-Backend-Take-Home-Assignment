package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.assets.Vehicle
import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule
import java.time.LocalDate

internal class VehicleAgeRule(private val vehicle: Vehicle) : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if(vehicleAge(vehicle.year) in (0..5))
            riskProfile.add(1)
        else
            riskProfile

    private fun vehicleAge(year: Int)= LocalDate.now().year - year
}