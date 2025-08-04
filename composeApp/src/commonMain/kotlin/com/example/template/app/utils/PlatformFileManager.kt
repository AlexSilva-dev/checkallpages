package com.example.template.app.utils

import io.github.vinceglb.filekit.PlatformFile

expect object PlatformFileManager {
    fun getPlatformFile(): PlatformFile?
    fun createPlatformFileFromPath(path: String): PlatformFile
    fun createDirectoryIfNotExist(platformFile: PlatformFile)
    fun listFolders(directory: PlatformFile): List<PlatformFile>

}
expect suspend fun PlatformFile.writeString(string: String)
expect suspend fun PlatformFile.resolve(relative: String): PlatformFile
expect suspend fun PlatformFile.exists(): Boolean
expect suspend fun PlatformFile.createDirectories(mustCreate: Boolean = false)
expect suspend fun PlatformFile.appendString(string: String)

expect suspend fun openFileWithSystemDefault(filePath: String): Boolean