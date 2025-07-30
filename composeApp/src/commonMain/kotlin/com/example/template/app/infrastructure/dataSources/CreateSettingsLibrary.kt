package com.example.template.app.infrastructure.dataSources

import com.russhwolf.settings.Settings

expect class CreateSettingsLibrary {
    fun execute(): Settings
}