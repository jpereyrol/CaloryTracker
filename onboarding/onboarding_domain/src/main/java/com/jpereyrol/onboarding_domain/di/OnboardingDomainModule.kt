package com.jpereyrol.onboarding_domain.di

import com.jpereyrol.onboarding_domain.use_case.ValidateNutrient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNutrient(): ValidateNutrient {
        return ValidateNutrient()
    }
}