package com.kashif.android

import android.app.Application
import com.kashif.common.domain.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(baseUrl = "https://api.themoviedb.org/3/", enableNetworkLogs = BuildConfig.DEBUG) {
            androidLogger()
            androidContext(this@App)
        }
    }
}
