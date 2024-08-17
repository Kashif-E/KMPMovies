package com.kashif.common

import android.app.Application
//import com.kashif.android.BuildConfig
import com.kashif.common.domain.di.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(baseUrl = "https://api.themoviedb.org/3/", enableNetworkLogs = true) {
            //  androidLogger()
            // androidContext(this@App)
        }
    }
}