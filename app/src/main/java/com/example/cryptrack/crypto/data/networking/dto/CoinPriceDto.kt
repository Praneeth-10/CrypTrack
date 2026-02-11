package com.example.cryptrack.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    val priceUsd : Double,
//    val date : Long,
    val time : Long
)
