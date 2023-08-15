package com.jpereyrol.onboarding_domain.use_case

import com.jpereyrol.core.util.UiText
import com.jpereyrol.onboarding_domain.R

class ValidateNutrient {

    operator fun invoke(
        carbRatioText: String,
        proteinRatioText: String,
        fatRatioText: String,
    ): Result {
        val carbsRatio = carbRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_invalid_values)
            )
        }

        if (carbsRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_not_100_percent)
            )
        }

        return Result.Success(
            carbsRatio = carbsRatio / 100f,
            proteinRatio = proteinRatio / 100f,
            fatRatio = fatRatio / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float,
        ): Result()

        data class Error(val message: UiText): Result()
    }
}