package com.kashif.android

import android.app.Application
import com.kashif.common.di.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            enableNetworkLogs = true,
            baseUrl = "https://api.themoviedb.org/3/",
            appDeclaration = {})
    }
}
