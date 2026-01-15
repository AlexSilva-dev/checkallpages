package com.example.template

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.template.app.App
import com.example.template.app.di.initializeKoin
import io.github.vinceglb.filekit.FileKit
import org.jetbrains.compose.resources.painterResource
import template.composeapp.generated.resources.Res
import template.composeapp.generated.resources.logo_png

fun main() = application {
    FileKit.init(appId = "checkallpages")


    Window(
        onCloseRequest = ::exitApplication,
        title = "CheckAllPages",
        icon = painterResource(Res.drawable.logo_png)
    ) {
        initializeKoin()
        App()
    }
}