package com.jpereyrol.core.util

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    object navigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}