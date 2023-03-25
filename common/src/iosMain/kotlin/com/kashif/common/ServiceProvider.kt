package com.kashif.common

import com.kashif.common.presentation.HomeScreenViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object ServiceProvider : KoinComponent {
    val screenModel = get<HomeScreenViewModel>()

}


