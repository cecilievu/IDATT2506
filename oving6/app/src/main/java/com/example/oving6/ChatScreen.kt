package com.example.oving6

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    isServer: Boolean?,
    messages: List<Message>,
    draft: String,
    onDraftChange: (String) -> Unit,
    onStartServer: () -> Unit,
    onStartClient: () -> Unit,
    onSend: () -> Unit,
    connectedCount: Int,
    status: Int,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (isServer) {
                            true -> stringResource(
                                R.string.server_title, connectedCount
                            )
                            false -> stringResource(
                                R.string.client_title, connectedCount
                            )
                            null -> stringResource(R.string.app_name)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isServer == null) {
                Button(onClick = onStartServer) {
                    Text(stringResource(R.string.start_server))
                }

                Button(onClick = onStartClient) {
                    Text(stringResource(R.string.connect))
                }
            } else {
                Text(text = stringResource(status))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(messages) { message ->
                        Text(
                            text = message.text,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                Row {
                    TextField(
                        value = draft,
                        onValueChange = onDraftChange,
                        label = {
                            Text(stringResource(R.string.message))
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = onSend,
                        enabled = draft.isNotBlank()
                    ) {
                        Text(stringResource(R.string.send))
                    }
                }
            }
        }
    }
}

@Composable
fun ChatScreenWithViewModel(
    gameViewModel: ChatViewModel = viewModel()
) {
    val isServer by gameViewModel.isServer.collectAsStateWithLifecycle()
    val messages by gameViewModel.messages.collectAsStateWithLifecycle()
    val connectedCount by gameViewModel.connectedCount.collectAsStateWithLifecycle()
    val status by gameViewModel.status.collectAsStateWithLifecycle()

    var draft by rememberSaveable { mutableStateOf("") }

    ChatScreen(
        isServer = isServer,
        messages = messages,
        draft = draft,
        onDraftChange = { draft = it },
        onStartServer = { gameViewModel.startAsServer() },
        onStartClient = { gameViewModel.startAsClient() },
        onSend = {
            gameViewModel.send(draft)
            draft = ""
        },
        connectedCount = connectedCount,
        status = status
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        isServer = true,
        messages = listOf(
            Message(false, "Klient 1: Hei!"),
            Message(true, "Meg: Hallo!"),
            Message(false, "Klient 2: Hei fra meg også!")
        ),
        draft = "",
        onDraftChange = {},
        onStartServer = {},
        onStartClient = {},
        onSend = {},
        connectedCount = 2,
        status = R.string.connected
    )
}