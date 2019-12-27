package com.origin.risk.domain.engine

import com.origin.risk.domain.customer.Customer

internal class RiskEngine(
    private val customer: Customer,
    private val riskQuestions: Array<Int>
) {
    fun riskProfile(name: String, rules: List<Rule>): RiskProfile {
        var profile = RiskProfile(name, true, Score(initialScore()))

        rules.forEach { rule ->
            profile = rule.apply(customer, profile)
        }

        return profile
    }

    private fun initialScore() = riskQuestions.sum()
}
