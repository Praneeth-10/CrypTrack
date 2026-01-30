package com.example.cryptrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptrack.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptrack.crypto.presentation.coin_list.CoinListViewModel
import com.example.cryptrack.ui.theme.CrypTrackTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrypTrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val event = viewModel.events
                    CoinListScreen(
                        coinListState = state,
                        events = event,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}