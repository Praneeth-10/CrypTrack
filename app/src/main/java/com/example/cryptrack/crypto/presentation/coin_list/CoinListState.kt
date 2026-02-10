package com.example.cryptrack.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.example.cryptrack.crypto.presentation.models.CoinUi

@Immutable
sealed interface CoinListState {
    object Loading : CoinListState
    data class CoinList(val coinList : List<CoinUi> ) : CoinListState
    data class SelectedCoin(val selectedCoin : CoinUi? = null) : CoinListState
}