package com.jpereyrol.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpereyrol.core.domain.model.ActivityLevel
import com.jpereyrol.core.domain.preferences.Preferences
import com.jpereyrol.core.domain.use_case.FilterOutDigits
import com.jpereyrol.core.navigation.Route
import com.jpereyrol.core.util.UiEvent
import com.jpereyrol.core.util.UiText
import com.jpereyrol.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val preferences: Preferences
): ViewModel() {

    var activityLevel by mutableStateOf<ActivityLevel>(
        ActivityLevel.Medium
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelSelect(activityLevel: ActivityLevel) {
        this.activityLevel = activityLevel
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveActivityLevel(activityLevel)
            _uiEvent.send(UiEvent.Navigate(Route.GOAL))
        }
    }
}