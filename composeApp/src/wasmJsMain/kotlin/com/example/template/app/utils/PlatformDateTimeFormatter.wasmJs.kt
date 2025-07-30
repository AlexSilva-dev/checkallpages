package com.example.template.app.utils

import kotlinx.datetime.TimeZone

// Usaremos esta única função externa robusta para ambos os casos.
// Ela usa a API Intl.DateTimeFormat, que é a forma moderna e correta.
@JsFun("""
  (value, locale, options) => {
    // se veio como BigInt, converte pra Number
    const v = typeof value === 'bigint' ? Number(value) : value;
    return new Intl.DateTimeFormat(locale, options).format(new Date(v));
  }
""")
private external fun jsIntlFormat(value: Long, locale: String?, options: JsAny): String

/**
 * A implementação 'actual' para a plataforma Web.
 */
internal actual object PlatformDateTimeFormatter {

    actual fun formatDate(
        timestampMillis: Long,
        style: DateStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String {
        // CORREÇÃO: Trata o 'localeId' nulo de forma segura.
        // Se for nulo, passamos 'null' para o JS, que o interpretará
        // como "use o locale padrão do navegador".
        val actualLocale: String? = localeId

        val options: JsAny = when (style) {
            DateStyle.SHORT -> jsDateStyleShort()
            DateStyle.MEDIUM -> jsDateStyleMedium()
            DateStyle.LONG -> jsDateStyleLong()
            DateStyle.WEEKDAY_ONLY -> jsDateStyleWeekdayOnly()
        }

        return jsIntlFormat(timestampMillis, actualLocale, options)
    }

    actual fun formatTime(
        timestampMillis: Long,
        style: TimeStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String {
        val actualLocale: String? = localeId

        val options: JsAny = when (style) {
            TimeStyle.SHORT -> jsTimeStyleShort()
            TimeStyle.MEDIUM -> jsTimeStyleMedium()
        }

        // CORREÇÃO: Agora usa a mesma função externa correta.
        return jsIntlFormat(timestampMillis, actualLocale, options)
    }
}

// --- Funções Auxiliares para Opções de Formatação (seu código está perfeito aqui) ---

private fun jsTimeStyleShort(): JsAny = js("({ timeStyle: 'short' })")
private fun jsTimeStyleMedium(): JsAny = js("({ timeStyle: 'medium' })")

private fun jsDateStyleShort(): JsAny = js("({ dateStyle: 'short' })")
private fun jsDateStyleMedium(): JsAny = js("({ dateStyle: 'medium' })")
private fun jsDateStyleLong(): JsAny = js("({ dateStyle: 'long' })")
private fun jsDateStyleWeekdayOnly(): JsAny = js("({ weekday: 'long' })")
