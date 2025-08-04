package com.example.template.app.utils

object UrlUtil {
    fun getDomain(url: String): String {
        var domain = url.replace("https://", "").replace("http://", "")
        domain = domain.split("/")[0]
        return domain
    }

    fun getPath(url: String): String {
        return url.replace("http://", "")
            .replace("https://", "")
            .replace(".", "-")
            .split("/")[1].toString()
    }
}