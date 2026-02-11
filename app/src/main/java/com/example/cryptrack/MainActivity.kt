package com.example.cryptrack

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptrack.core.presentation.util.ObserveAsEvents
import com.example.cryptrack.core.presentation.util.toString
import com.example.cryptrack.crypto.presentation.coin_detail.CoinDetailScreen
import com.example.cryptrack.crypto.presentation.coin_list.CoinListEvent
import com.example.cryptrack.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptrack.crypto.presentation.coin_list.CoinListState
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
                    val context = LocalContext.current
                    ObserveAsEvents(events = viewModel.events) { event ->
                        when (event) {
                            is CoinListEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    event.error.toString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    /**
                     * Navigating
                     */
                    when(state){
                        is CoinListState.CoinList -> {
                            CoinListScreen(
                                coinListState = state,
                                modifier = Modifier.padding(innerPadding),
                                onAction = viewModel :: onAction
                            )
                        }
                        CoinListState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is CoinListState.SelectedCoin -> {
                            CoinDetailScreen(
                                coinListState = state,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }
                }
            }
        }
    }
}