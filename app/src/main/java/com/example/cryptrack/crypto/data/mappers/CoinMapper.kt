package com.example.cryptrack.crypto.data.mappers

import com.example.cryptrack.crypto.data.networking.dto.CoinDto
import com.example.cryptrack.crypto.data.networking.dto.CoinPriceDto
import com.example.cryptrack.crypto.domain.Coin
import com.example.cryptrack.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin() : Coin {
    return Coin(
        id = this.id,
        rank = this.rank,
        name = this.name,
        symbol = this.symbol,
        marketCapUsd = this.marketCapUsd,
        priceUsd = this.priceUsd,
        changePercent24Hr = this.changePercent24Hr,
    )
}

fun CoinPriceDto.toCoinPrice() : CoinPrice{
    return CoinPrice(
        priceUsd = this.priceUsd,
//        date = Instant
//            .ofEpochMilli(this.date)
//            .atZone(ZoneId.of("UTC")),
        time = Instant
            .ofEpochMilli(this.time)
            .atZone(ZoneId.of("UTC")),
    )
}