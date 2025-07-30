package com.example.template.app.infrastructure.dataSources

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer

class SettingsDataStoreImpl(
    private val createSettingsLibrary: CreateSettingsLibrary
): SettingsDataStore {
    private val settings: Settings = createSettingsLibrary.execute()
    override fun save(key: String, value: String) {
        return this.settings.set(key = key, value = value)
    }

    override fun save(key: String, value: Int) {
        this.settings.set(key = key, value = value)
    }

    override fun save(key: String, value: Long) {
        this.settings.set(key = key, value = value)
    }

    override fun save(key: String, value: Float) {
        this.settings.set(key = key, value = value)
    }

    override fun save(key: String, value: Double) {
        this.settings.set(key = key, value = value)
    }

    override fun save(key: String, value: Boolean) {
        this.settings.set(key = key, value = value)
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun <T> save(key: String, serializer: KSerializer<T>, value: T) {
        this.settings.encodeValue(
            key = key,
            serializer = serializer,
            value = value
        )
    }

    override fun getString(key: String, defaultValue: String): String {
        return this.settings.get(key = key, defaultValue = defaultValue)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return this.settings.get(key = key, defaultValue = defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return this.settings.get(key = key, defaultValue = defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return this.settings.get(key = key, defaultValue = defaultValue)
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return this.settings.get(key = key, defaultValue = defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return this.settings.get(key = key, defaultValue = defaultValue)
    }

    override fun remove(key: String) {
        this.settings.remove(key = key)
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun <T> getObject(
        key: String,
        default: T,
        serializer: KSerializer<T>
    ): T {
        return this.settings.decodeValue(
            key = key,
            defaultValue = default,
            serializer = serializer
        )
    }
}