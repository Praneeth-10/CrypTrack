package com.example.cryptrack.crypto.data.networking

import com.example.cryptrack.core.data.networking.constructUrl
import com.example.cryptrack.core.data.networking.safeCall
import com.example.cryptrack.core.domain.util.NetworkError
import com.example.cryptrack.core.domain.util.Result
import com.example.cryptrack.core.domain.util.map
import com.example.cryptrack.crypto.data.mappers.toCoin
import com.example.cryptrack.crypto.data.networking.dto.CoinsResponseDto
import com.example.cryptrack.crypto.domain.Coin
import com.example.cryptrack.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("assets")
            )

        }.map { response ->
            response.data.map {
                it.toCoin()
            }
        }
    }
}