package com.example.cryptrack.crypto.domain

import com.example.cryptrack.core.domain.util.NetworkError
import com.example.cryptrack.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins() : Result<List<Coin>, NetworkError>
}