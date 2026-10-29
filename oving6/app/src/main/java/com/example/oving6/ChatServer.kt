package com.example.oving6

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket

class ChatServer(private val port: Int) {

    private var serverSocket: ServerSocket? = null

    suspend fun awaitClient(): Socket = withContext(Dispatchers.IO) {
        val listening = serverSocket ?: ServerSocket(port)
        serverSocket = listening
        listening.accept()
    }

    fun close() {
        serverSocket?.close()
    }
}