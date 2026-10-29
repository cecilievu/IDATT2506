package com.example.oving6

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class Connection(private val socket: Socket) {

    private val reader = BufferedReader(
        InputStreamReader(socket.getInputStream(), Charsets.UTF_8)
    )

    private val writer = BufferedWriter(
        OutputStreamWriter(socket.getOutputStream(), Charsets.UTF_8)
    )

    suspend fun send(message: String) = withContext(Dispatchers.IO) {
        writer.write(message)
        writer.newLine()
        writer.flush()
    }

    suspend fun receive(): String? = withContext(Dispatchers.IO) {
        reader.readLine()
    }

    fun close() {
        socket.close()
    }
}