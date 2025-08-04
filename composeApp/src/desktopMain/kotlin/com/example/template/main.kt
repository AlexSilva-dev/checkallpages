package com.example.template

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.template.app.App
import com.example.template.app.di.initializeKoin
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile

fun main() = application {
    FileKit.init(appId = "MyApplication")
    val file = PlatformFile("/path/to/file.txt")


    Window(
        onCloseRequest = ::exitApplication,
        title = "template",
    ) {
        initializeKoin()
        App()
    }
}