package com.example.template.app.infrastructure.dataSources

import com.russhwolf.settings.ExperimentalSettingsApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer

interface SettingsDataStore {
    // Métodos para salvar valores
    fun save(key: String, value: String)
    fun save(key: String, value: Int)
    fun save(key: String, value: Long)
    fun save(key: String, value: Float)
    fun save(key: String, value: Double)
    fun save(key: String, value: Boolean)

    // Métodos para recuperar valores com valor padrão
    fun getString(key: String, defaultValue: String): String
    fun getInt(key: String, defaultValue: Int): Int
    fun getLong(key: String, defaultValue: Long): Long
    fun getFloat(key: String, defaultValue: Float): Float
    fun getDouble(key: String, defaultValue: Double): Double
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun remove(key: String)
    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    fun <T> save(key: String, serializer: KSerializer<T>, value: T)
    fun <T> getObject(key: String, default: T, serializer: KSerializer<T>): T
}
