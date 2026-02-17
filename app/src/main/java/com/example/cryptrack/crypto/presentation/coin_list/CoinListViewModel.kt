package com.example.cryptrack.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptrack.core.domain.util.onError
import com.example.cryptrack.core.domain.util.onSuccess
import com.example.cryptrack.crypto.domain.CoinDataSource
import com.example.cryptrack.crypto.presentation.coin_detail.DataPoint
import com.example.cryptrack.crypto.presentation.models.CoinUi
import com.example.cryptrack.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow<CoinListState>(CoinListState.Loading)
    val state: StateFlow<CoinListState> =
        _state.onStart { loadCoins() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = CoinListState.Loading
            )

    /**
     * Creating channel similar to shared Flows
     * as we need to show the error message only once
     */
    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()



    /**
     * We load the coins or data only in view model not from any Activities or fragments.
     * WE LOAD it when the View Model is initiated for that screen.
     */
    private fun loadCoins() {
        viewModelScope.launch {

            /**
             * NOT Needed as we are already in the Loading Sate
             */
            _state.update {
                CoinListState.Loading
            }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        CoinListState.CoinList(coinList = coins.map {
                            it.toCoinUi()
                        })
                    }
                }
                .onError { error ->
                    _events.send(element = CoinListEvent.Error(error))
                }
        }
    }


    fun onAction(action : CoinListAction) {
        when(action){
            is CoinListAction.OnCoinClick -> {
                // Clicking the action, so showing a loading for a split second
                _state.update {
                    CoinListState.Loading
                }
                /**
                 * updating the selected coin on click event in the list
                 */
                _state.update {
                    CoinListState.SelectedCoin(selectedCoin = action.coinUi)
                }

                selectCoin(action.coinUi)
            }
            CoinListAction.OnRefresh -> {
                loadCoins()
            }
        }
    }

    private fun selectCoin(coinUi : CoinUi){
        _state.update {
            CoinListState.SelectedCoin(selectedCoin = coinUi)
        }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            )
                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.time }
                        .map {
                            DataPoint(
                                x = it.time.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter.ofPattern("ha\nM/d")
                                    .format(it.time)
                            )
                        }

                    _state.update {
                        CoinListState.SelectedCoin(selectedCoin = coinUi.copy(
                            coinPriceHistory = dataPoints
                        ))
                    }
                    println(history)
                }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}