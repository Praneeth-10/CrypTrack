package com.example.cryptrack.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptrack.core.domain.util.onError
import com.example.cryptrack.core.domain.util.onSuccess
import com.example.cryptrack.crypto.domain.CoinDataSource
import com.example.cryptrack.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                    _state.update {
                        CoinListState.Error(message = error.name)
                    }
                }
        }
    }


    fun onAction(action : CoinListAction) {
        when(action){
            is CoinListAction.OnCoinClick -> {
                TODO()
            }
            CoinListAction.OnRefresh -> {
                loadCoins()
            }
        }
    }
}