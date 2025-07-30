package com.example.template.app.plugins

import com.example.template.app.utils.DATABASE_DRIVER
import com.example.template.app.utils.DATABASE_PASSWORD
import com.example.template.app.utils.DATABASE_URL
import com.example.template.app.utils.DATABASE_USER
import com.example.template.pagespeed.infrastructure.database.tables.AlternativesTable
import com.example.template.pagespeed.infrastructure.database.tables.QuizzesTable
import com.example.template.pagespeed.infrastructure.database.tables.TopicsTable
import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabase() {
    val driver = DATABASE_DRIVER
    val url = DATABASE_URL
    val user = DATABASE_USER
    val password = DATABASE_PASSWORD

    if (
        driver.isNullOrEmpty()
        || url.isNullOrEmpty()
        || user.isNullOrEmpty()
        || password.isNullOrEmpty()
        || url.isEmpty()
    ) {
        throw IllegalAccessException("Ambient variables not set")
    }
    Database.connect(url, driver = driver, user = user, password = password)
    transaction {
        SchemaUtils.create(
            TopicsTable,
            AlternativesTable,
            QuizzesTable,
        )
    }
    println("Conexão com o banco de dados estabelecida com sucesso.")
}