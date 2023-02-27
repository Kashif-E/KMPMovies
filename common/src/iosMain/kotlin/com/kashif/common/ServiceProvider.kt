package com.kashif.common

import com.kashif.common.presentation.MoviesScreenModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object ServiceProvider : KoinComponent {
    val screenModel = get<MoviesScreenModel>()

}


