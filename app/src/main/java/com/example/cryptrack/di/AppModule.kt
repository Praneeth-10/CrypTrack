package com.example.cryptrack.di

import com.example.cryptrack.core.data.networking.HttpClientFactory
import com.example.cryptrack.crypto.data.networking.RemoteCoinDataSource
import com.example.cryptrack.crypto.domain.CoinDataSource
import com.example.cryptrack.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(CIO.create())
    }

    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}