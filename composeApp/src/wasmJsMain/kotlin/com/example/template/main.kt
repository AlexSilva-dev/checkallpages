package com.example.template

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.template.app.App
import com.example.template.app.di.initializeKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    try {

        initializeKoin()
        ComposeViewport(document.body!!) {
            App()
        }
    } catch (e: Exception) {
        println(e.toString())
    }
}