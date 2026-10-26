package com.example.oving5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.CookieHandler
import java.net.CookieManager
import java.net.URL
import java.net.URLEncoder

class HttpWrapper (private val url: String) {
    init {
        CookieHandler.setDefault(CookieManager())
    }

    suspend fun get(parameters: Map<String, String>): String =
        withContext(Dispatchers.IO) {
            val query = encodeParameters(parameters)
            val connection = URL("$url?$query").openConnection()
            connection.inputStream.reader().use { it.readText() }
        }

    suspend fun post(parameters: Map<String, String>): String =
        withContext(Dispatchers.IO) {
            val connection = URL(url).openConnection()
            val type = "application/x-www-form-urlencoded; charset=UTF-8"
            connection.setRequestProperty("Content-Type", type)
            connection.doOutput = true
            val body = encodeParameters(parameters).toByteArray()
            connection.outputStream.use { it.write(body) }
            connection.inputStream.reader().use { it.readText() }
        }

    suspend fun getWithHeaders(parameters: Map<String, String>): String =
        withContext(Dispatchers.IO) {
            val query = encodeParameters(parameters)
            val connection = URL("$url?$query").openConnection()
            val headers = connection.headerFields.entries
            headers.joinToString("\n") + "\n\n" +
                    connection.inputStream.reader().use { it.readText() }
            }

    fun encodeParameters(parameters: Map<String, String>): String =
        parameters.entries.joinToString("&") { entry ->
            encode(entry.key) + "=" + encode(entry.value)
        }
    private fun encode(text: String): String =
        URLEncoder.encode(text, "UTF-8")
}