package com.example.cryptrack.crypto.presentation.coin_list

import com.example.cryptrack.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error : NetworkError) : CoinListEvent
}