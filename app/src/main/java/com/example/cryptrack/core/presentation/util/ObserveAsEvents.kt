package com.example.cryptrack.core.presentation.util

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ObserveAsEvents(
    events : Flow<T>,
    key1 : Any? = null,
    key2 : Any? = null,
    onEvent : (T) -> Unit
) {

}