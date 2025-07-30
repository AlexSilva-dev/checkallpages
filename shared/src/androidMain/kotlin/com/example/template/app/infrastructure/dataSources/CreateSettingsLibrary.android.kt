package com.example.template.app.infrastructure.dataSources

import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings


actual class CreateSettingsLibrary(
    private val context: Context
) {
    actual fun execute(): Settings {
        val delegate: SharedPreferences = context.getSharedPreferences(
            /* name = */ "ddf",
            /* mode = */ Context.MODE_PRIVATE
        )

        val settings: Settings = SharedPreferencesSettings(
            delegate = delegate
        )
        return settings
    }
}