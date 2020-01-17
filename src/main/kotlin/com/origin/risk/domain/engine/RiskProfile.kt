package com.origin.risk.domain.engine

typealias ProfileStatusFactory = (riskProfile: RiskProfile) -> ProfileStatus

open class RiskProfile(
    val name: String = "",
    val eligible: Boolean = false,
    val score: Score = Score(0),
    val profileStatusFactory: ProfileStatusFactory = ::baseProfileStatus
) {
    fun ineligible() = RiskProfile(
        name,
        false,
        score,
        profileStatusFactory
    )

    fun deduct(points: Int) = RiskProfile(
        name,
        eligible,
        score.subtract(points),
        profileStatusFactory
    )

    fun add(points: Int) = RiskProfile(
        name,
        eligible,
        score.add(points),
        profileStatusFactory
    )

    val status: ProfileStatus get() = profileStatusFactory(this)

    companion object {
        internal fun baseProfileStatus(riskProfile: RiskProfile): ProfileStatus {
            if(riskProfile.eligible) {
                return when {
                    riskProfile.score.value <= 0 -> ProfileStatus.ECONOMIC
                    riskProfile.score.value in (1..2) -> ProfileStatus.REGULAR
                    else -> ProfileStatus.RESPONSIBLE
                }
            }

            return ProfileStatus.INELIGIBLE
        }
    }
}

enum class ProfileStatus {
    REGULAR,
    INELIGIBLE,
    ECONOMIC,
    RESPONSIBLE
}