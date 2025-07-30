package com.example.template.app.infrastructure.dataSources

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class CreateSettingsLibrary {
    actual fun execute(): Settings {
        val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults()
        val settings: Settings = NSUserDefaultsSettings(delegate)
        return settings
    }
}