package com.origin.risk.web.support

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidRiskQuestionsValidator::class])
annotation class ValidRiskQuestions(
    val message: String = "invalid risk question value",
    val groups: Array<KClass<out String>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class ValidRiskQuestionsValidator : ConstraintValidator<ValidRiskQuestions, Array<Int>?> {
    override fun isValid(riskQuestions: Array<Int>?, context: ConstraintValidatorContext) =
        !riskQuestions.isNullOrEmpty() && riskQuestions.all { it == 0 || it == 1 }
}