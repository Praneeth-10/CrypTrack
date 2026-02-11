package com.example.cryptrack.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val priceUsd : Double,
//    val date : ZonedDateTime,
    val time  : ZonedDateTime
)
