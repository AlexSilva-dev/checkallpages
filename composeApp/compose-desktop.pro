# Regras básicas para Compose Desktop
-dontwarn javax.swing.**
-dontwarn java.awt.**
-dontwarn org.slf4j.**

# Suprimir avisos de bibliotecas KMP comuns que referenciam Android ou classes opcionais
-dontwarn kotlinx.datetime.**
-dontwarn androidx.compose.material3.internal.**
-dontwarn io.ktor.**
-dontwarn nl.adaptivity.xmlutil.**
-dontwarn com.sun.jna.**
-dontwarn org.freedesktop.dbus.**

# Manter classes do Compose e Skiko
-keep class androidx.compose.** { *; }
-keep class org.jetbrains.skiko.** { *; }
-keep class org.jetbrains.skia.** { *; }

# Manter classes principais da sua aplicação (ajuste conforme o pacote)
-keep class com.example.template.** { *; }

# Serialização e Ktor
-keep class io.ktor.** { *; }
-keep class kotlinx.serialization.** { *; }
