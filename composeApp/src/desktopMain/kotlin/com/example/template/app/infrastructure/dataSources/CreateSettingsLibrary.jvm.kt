package com.example.template.app.infrastructure.dataSources

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences


actual class CreateSettingsLibrary {
    actual fun execute(): Settings {
        return PreferencesSettings(delegate = Preferences.userRoot())
    }
}