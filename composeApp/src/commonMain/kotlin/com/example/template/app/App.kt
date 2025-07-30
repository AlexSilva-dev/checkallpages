package com.example.template.app

import androidx.compose.runtime.*
import com.example.template.app.navigations.AppNavegation
import com.example.template.app.viewModels.AppViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
        KoinContext {
                val appViewModel: AppViewModel = koinInject<AppViewModel>()
                AppNavegation()
        }
}

