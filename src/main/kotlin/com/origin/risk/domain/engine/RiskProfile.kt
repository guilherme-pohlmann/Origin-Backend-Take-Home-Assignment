package com.origin.risk.domain.engine

data class RiskProfile(
    val name: String = "",
    val eligible: Boolean = false,
    val score: Score = Score(0)
) {
    fun ineligible() = RiskProfile(
        name,
        false,
        score
    )

    fun deduct(points: Int) = RiskProfile(
        name,
        eligible,
        score.subtract(points)
    )

    fun add(points: Int) = RiskProfile(
        name,
        eligible,
        score.add(points)
    )

    val status: ProfileStatus get() {
        if(eligible) {
            return when {
                score.value <= 0 -> ProfileStatus.ECONOMIC
                score.value in (1..2) -> ProfileStatus.REGULAR
                else -> ProfileStatus.RESPONSIBLE
            }
        }

        return ProfileStatus.INELIGIBLE
    }
}

enum class ProfileStatus {
    REGULAR,
    INELIGIBLE,
    ECONOMIC,
    RESPONSIBLE
}