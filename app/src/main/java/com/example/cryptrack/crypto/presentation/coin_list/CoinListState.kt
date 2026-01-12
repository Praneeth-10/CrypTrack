package com.example.cryptrack.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.example.cryptrack.crypto.presentation.models.CoinUi

@Immutable
sealed interface CoinListState {
    data object Loading : CoinListState
    data class CoinList(val coinList : List<CoinUi> ) : CoinListState
    data class Error(val message: String) : CoinListState
    data class SelectedCoin(val selectedCoin : CoinUi? = null) : CoinListState
}