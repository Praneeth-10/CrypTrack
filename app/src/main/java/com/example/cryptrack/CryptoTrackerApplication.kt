package com.example.cryptrack

import android.app.Application
import com.example.cryptrack.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoTrackerApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoTrackerApplication)
            androidLogger()

            modules(modules = appModule)
        }
    }
}