package com.example.cryptrack.crypto.presentation.models

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
import com.example.cryptrack.crypto.domain.Coin
import com.example.cryptrack.core.presentation.util.getDrawableIdForCoin
import com.example.cryptrack.crypto.presentation.coin_detail.DataPoint
import java.util.Locale

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint> = emptyList(),
    @param:DrawableRes @field:DrawableRes
    val iconRes: Int,
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String,
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = this.id,
        rank = this.rank,
        name = this.name,
        symbol = this.symbol,
        marketCapUsd = this.marketCapUsd.toDisplayableNumber(),
        priceUsd = this.priceUsd.toDisplayableNumber(),
        changePercent24Hr = this.changePercent24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol = this.symbol),
    )
}

fun Double.toDisplayableNumber(): DisplayableNumber{
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}