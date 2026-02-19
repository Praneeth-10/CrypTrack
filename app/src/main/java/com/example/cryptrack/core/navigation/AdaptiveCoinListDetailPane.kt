@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.example.cryptrack.core.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptrack.core.presentation.util.ObserveAsEvents
import com.example.cryptrack.core.presentation.util.toString
import com.example.cryptrack.crypto.presentation.coin_detail.CoinDetailScreen
import com.example.cryptrack.crypto.presentation.coin_list.CoinListAction
import com.example.cryptrack.crypto.presentation.coin_list.CoinListEvent
import com.example.cryptrack.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptrack.crypto.presentation.coin_list.CoinListState
import com.example.cryptrack.crypto.presentation.coin_list.CoinListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {

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

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scope = rememberCoroutineScope()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            CoinListScreen(
                coinListState = state,
                onAction = { action ->
                    when (action) {
                        is CoinListAction.OnCoinClick -> {
                            viewModel.onAction(action = action)
//                                viewModel.navigateToDetail(navigator)
                            scope.launch {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        }

                        CoinListAction.OnRefresh -> {
                            Toast.makeText(context, "Refreshed", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            )
        },
        detailPane = {
            AnimatedPane {
                when (state) {
                    is CoinListState.CoinList -> {
                        if ((state as CoinListState.CoinList).selectedCoin != null){
                            CoinDetailScreen(
                                selectedCoin = (state as CoinListState.CoinList).selectedCoin
                            )
                        }
                    }

                    CoinListState.Loading -> {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        },
        modifier = modifier
    )
}