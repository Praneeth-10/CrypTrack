package com.example.cryptrack.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvents(
    events : Flow<T>,
    key1 : Any? = null,
    key2 : Any? = null,
    onEvent : (T) -> Unit
) {
    /**
     * For Error handling to be only collected once even after Configuration changes.
     * No need to display the error message again.
     * And handling events for a corner case when event is lost, we are adding 'Main.immediate' context
     * making it run when only on Main thread and in foreground
     */
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner,key1,key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            withContext(Dispatchers.Main.immediate){
                events.collect(onEvent)
            }
        }
    }
}