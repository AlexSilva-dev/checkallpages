package com.example.template.app.utils

import java.io.InputStream

object ResourceIO {
    fun loadResourceContent(resourcePath: String): InputStream {

        val classLoader = Thread.currentThread().contextClassLoader
        val inputStream: InputStream? =
            classLoader.getResourceAsStream(resourcePath)

        if (inputStream == null) {
            throw IllegalArgumentException("Resource not found: $resourcePath")
        }

        // Ler o conteúdo do InputStream
        return inputStream
    }
}