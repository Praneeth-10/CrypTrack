package com.example.cryptrack.crypto.data.networking

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cryptrack.core.data.networking.constructUrl
import com.example.cryptrack.core.data.networking.safeCall
import com.example.cryptrack.core.domain.util.NetworkError
import com.example.cryptrack.core.domain.util.Result
import com.example.cryptrack.core.domain.util.map
import com.example.cryptrack.crypto.data.mappers.toCoin
import com.example.cryptrack.crypto.data.mappers.toCoinPrice
import com.example.cryptrack.crypto.data.networking.dto.CoinHistoryDto
import com.example.cryptrack.crypto.data.networking.dto.CoinsResponseDto
import com.example.cryptrack.crypto.domain.Coin
import com.example.cryptrack.crypto.domain.CoinDataSource
import com.example.cryptrack.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("assets")
            ) {
                header(
                    key = HttpHeaders.Authorization,
                    value = " Bearer 2b16c2ae8af20e65fe63cd6b4f2d8be300b86905a59d56bafa2b4118de186946"
                )
            }

        }.map { response ->
            response.data.map {
                it.toCoin()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()


        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                header(
                    key = HttpHeaders.Authorization,
                    value = " Bearer 2b16c2ae8af20e65fe63cd6b4f2d8be300b86905a59d56bafa2b4118de186946"
                )
                parameter(
                    key = "interval",
                    value = "h6"
                )
                parameter(
                    key = "start",
                    value = startMillis
                )
                parameter(
                    key = "end",
                    value = endMillis
                )
            }
        }.map { response ->
            response.data.map {
                it.toCoinPrice()
            }
        }
    }
}