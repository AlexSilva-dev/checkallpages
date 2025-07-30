package com.example.template.app.infrastructure.dataSources

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual class CreateSettingsLibrary {
    actual fun execute(): Settings {
        val settings: Settings = StorageSettings()

        return settings
    }
}