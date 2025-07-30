package com.example.template

import androidx.compose.ui.window.ComposeUIViewController
import com.example.template.app.App
import com.example.template.app.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) {
    App()
}