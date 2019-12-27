package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule
import java.time.LocalDate

internal object VehicleAgeRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if(customer.vehicle.exists { vehicleAge(it.year) in (0..5) })
            riskProfile.add(1)
        else
            riskProfile

    private fun vehicleAge(year: Int)= LocalDate.now().year - year
}