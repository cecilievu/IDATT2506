package com.example.oving6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

private const val PORT = 12345
private const val HOST = "10.0.2.2"

data class Message(
    val fromMe: Boolean,
    val text: String
)

class ChatViewModel : ViewModel() {

    private val server = ChatServer(PORT)
    private val client = ChatClient(HOST, PORT)

    private val connections = mutableListOf<Connection>()
    private val clientNames = mutableMapOf<Connection, String>()
    private var nextClientId = 1
    private var connection: Connection? = null
    private val _isServer = MutableStateFlow<Boolean?>(null)
    val isServer: StateFlow<Boolean?> = _isServer.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _connectedCount = MutableStateFlow(0)
    val connectedCount: StateFlow<Int> = _connectedCount.asStateFlow()

    private val _status = MutableStateFlow(R.string.ready)
    val status: StateFlow<Int> = _status.asStateFlow()

    private fun add(message: Message) {
        _messages.value = _messages.value + message
    }

    fun startAsServer() {
        if (_isServer.value != null) return
        _isServer.value = true
        _status.value = R.string.waiting_for_clients

        viewModelScope.launch {
            try {
                while (true) {
                    val socket = server.awaitClient()
                    val open = Connection(socket)

                    connections.add(open)

                    val clientName = "Klient ${nextClientId++}"
                    clientNames[open] = clientName

                    _connectedCount.value = connections.size
                    _status.value = R.string.connected

                    launch {
                        listenToClient(open)
                    }
                }
            } catch (e: IOException) {
                _status.value = R.string.server_stopped
            }
        }
    }

    private suspend fun listenToClient(open: Connection) {
        try {
            while (true) {
                val line = open.receive() ?: break

                val sender = clientNames[open] ?: "Ukjent klient"
                val formattedMessage = "$sender: $line"

                add(Message(false, formattedMessage))

                // sender meldingen videre til alle andre klienter
                for (other in connections.toList()) {
                    if (other != open) {
                        try {
                            other.send(formattedMessage)
                        } catch (e: IOException) {
                            // mottaksløkken rydder opp ved frakobling
                        }
                    }
                }
            }
        } catch (e: IOException) {
            // lukker forbindelsen
        } finally {
            connections.remove(open)
            clientNames.remove(open)
            open.close()
            _connectedCount.value = connections.size
            add(Message(false, "En klient koblet fra"))
        }
    }

    fun startAsClient() {
        if (_isServer.value != null) return
        _isServer.value = false
        _status.value = R.string.connecting

        viewModelScope.launch {
            try {
                val socket = client.connect()
                val open = Connection(socket)
                connection = open
                _connectedCount.value = 1
                _status.value = R.string.connected

                try {
                    while (true) {
                        val line = open.receive() ?: break
                        add(Message(false, "Mottatt: $line"))
                    }
                } finally {
                    _connectedCount.value = 0
                    _status.value = R.string.connection_ended
                    open.close()
                }
            } catch (e: IOException) {
                _connectedCount.value = 0
                _status.value = R.string.connection_failed
            }
        }
    }

    fun send(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            if (_isServer.value == true) {
                add(Message(true, "Meg: $text"))

                for (open in connections.toList()) {
                    try {
                        open.send("Tjener: $text")
                    } catch (e: IOException) {
                        // mottaksløkken rydder opp ved frakobling
                    }
                }
            } else {
                val open = connection ?: return@launch

                try {
                    open.send(text)
                    add(Message(true, "Meg: $text"))
                } catch (e: IOException) {
                    _status.value = R.string.send_failed
                }
            }
        }
    }

    override fun onCleared() {
        connection?.close()
        connections.forEach { it.close() }
        server.close()
        client.close()
        super.onCleared()
    }
}