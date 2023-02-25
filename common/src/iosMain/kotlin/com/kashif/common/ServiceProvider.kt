package com.kashif.common

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object ServiceProvider : KoinComponent {
    val screenModel = get<MoviesScreenModel>()

}