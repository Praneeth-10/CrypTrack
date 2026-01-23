package com.example.cryptrack.crypto.presentation.coin_list

import com.example.cryptrack.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi : CoinUi) : CoinListAction
    data object OnRefresh : CoinListAction
}