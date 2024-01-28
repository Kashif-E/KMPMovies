package com.kashif.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kashif.common.main.Application
import org.jetbrains.compose.resources.ExperimentalResourceApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalResourceApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Application()
        }
    }
}