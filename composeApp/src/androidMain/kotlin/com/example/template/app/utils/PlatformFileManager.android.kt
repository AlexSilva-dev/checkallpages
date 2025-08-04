package com.example.template.app.utils

import android.content.Context
import io.github.vinceglb.filekit.PlatformFile
import java.io.File

actual object PlatformFileManager {
    private var applicationContext: Context? = null

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    actual fun getPlatformFile(): PlatformFile? {
        return applicationContext?.filesDir?.let { PlatformFile(it) }
    }

    actual fun createPlatformFileFromPath(path: String): PlatformFile {
        return PlatformFile(File(path))
    }

    actual fun createDirectoryIfNotExist(platformFile: PlatformFile) {
        val file = File(platformFile.path)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    actual fun listFolders(directory: PlatformFile): List<PlatformFile> {
        val file = File(directory.path)
        if (file.isDirectory) {
            return file.listFiles { it.isDirectory }?.map { PlatformFile(it) } ?: emptyList()
        }
        return emptyList()
    }
}

actual suspend fun PlatformFile.writeString(string: String) {
    this.writeString(string)
}

actual suspend fun PlatformFile.resolve(relative: String): PlatformFile = this.resolve(relative)

actual suspend fun PlatformFile.exists(): Boolean {
    return this.exists()
}

actual suspend fun PlatformFile.appendString(string: String) {
    val sink = this.sink(
        append = true
    ).buffered()

    sink.use { bufferedSink ->
        // Use writeString para escrever o texto
        bufferedSink.writeString(string)
    }
}

actual suspend fun PlatformFile.createDirectories(mustCreate: Boolean) {
    this.createDirectories(mustCreate)
}

actual suspend fun openFileWithSystemDefault(filePath: String): Boolean {
    // Implementation for Android can be added here
    return false
}
