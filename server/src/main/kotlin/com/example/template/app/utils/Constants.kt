package com.example.template.app.utils

import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv()

val IS_DEVELOPMENT: Boolean = System.getProperty("is_development").toBoolean()

val DATABASE_DRIVER: String? = dotenv["DATABASE_DRIVER"]
val DATABASE_URL: String? = dotenv["DATABASE_URL"]
val DATABASE_USER: String? = dotenv["DATABASE_USER"]
val DATABASE_PASSWORD: String? = dotenv["DATABASE_PASSWORD"]

val GEMINI_API_KEY: String? = dotenv["GEMINI_API_KEY"]