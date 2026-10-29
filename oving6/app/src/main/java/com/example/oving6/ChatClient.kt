package com.example.oving6

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Socket

class ChatClient(
    private val host: String,
    private val port: Int
) {
    private var socket: Socket? = null

    suspend fun connect(): Socket = withContext(Dispatchers.IO) {
        val opened = Socket(host, port)
        socket = opened
        opened
    }

    fun close() {
        socket?.close()
    }
}