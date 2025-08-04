package com.example.template.app.utils

import io.github.vinceglb.filekit.*
import io.github.vinceglb.filekit.createDirectories
import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.resolve
import io.github.vinceglb.filekit.writeString
import kotlinx.io.IOException
import kotlinx.io.buffered
import kotlinx.io.writeString
import java.awt.Desktop
import java.io.File

actual object PlatformFileManager {

    /**
     * Pega a PlatformFile com o caminho base
     */
    actual fun getPlatformFile(): PlatformFile? {
        return FileKit.filesDir
    }

    actual fun createPlatformFileFromPath(path: String): PlatformFile {
        return PlatformFile(File(path))
    }

    actual fun createDirectoryIfNotExist(platformFile: PlatformFile) {
        if (!platformFile.exists()) {
            platformFile.createDirectories()
        }
    }

    actual fun listFolders(directory: PlatformFile): List<PlatformFile> {
        val file = File(directory.path + "/")
        if (file.isDirectory) {
            val files = file.listFiles { it.isDirectory }
            return files?.map { PlatformFile(it) } ?: emptyList()
        }
        return emptyList()
    }

}

actual suspend fun PlatformFile.writeString(string: String) {
    this.writeString(string)
}

actual suspend fun PlatformFile.resolve(relative: String): PlatformFile =
    this.resolve(relative)

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
    return try {
        val file =
            File(filePath) // Cria uma instância de java.io.File com o caminho fornecido

        // Verifica se a API Desktop é suportada no ambiente atual
        // (Nem todo ambiente JVM, como servidores headless, suporta GUI/Desktop)
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()

            // Verifica se a ação de 'abrir' é suportada para o tipo de arquivo/pasta
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file) // Isso instrui o SO a abrir o arquivo/pasta com o app padrão
                true // Sucesso na solicitação
            } else {
                println("Ação 'OPEN' não suportada pelo Desktop para este ambiente/tipo de arquivo: $filePath")
                false
            }
        } else {
            println("API Desktop não suportada neste ambiente JVM.")
            false
        }
    } catch (e: IOException) {
        // Erro de I/O, por exemplo, arquivo não encontrado, permissão negada, etc.
        println("Erro de I/O ao abrir arquivo/pasta '$filePath': ${e.message}")
        false
    } catch (e: SecurityException) {
        // Erro de segurança, por exemplo, sandbox de segurança restrita
        println("Erro de segurança ao abrir arquivo/pasta '$filePath': ${e.message}")
        false
    } catch (e: Exception) {
        // Qualquer outro erro inesperado
        println("Erro inesperado ao abrir arquivo/pasta '$filePath': ${e.message}")
        e.printStackTrace()
        false
    }
}

