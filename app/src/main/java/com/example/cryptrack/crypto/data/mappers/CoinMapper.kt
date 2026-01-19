package com.example.cryptrack.crypto.data.mappers

import com.example.cryptrack.crypto.data.networking.dto.CoinDto
import com.example.cryptrack.crypto.domain.Coin

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