package com.example.oving5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oving5.ui.theme.Oving5Theme

@Composable
fun GameScreen(
    name: String,
    onNameChange: (String) -> Unit,
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    guess: String,
    onGuessChange: (String) -> Unit,
    onStartGame: () -> Unit,
    response: String,
    onSubmitGuess: () -> Unit,
    gameStarted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (!gameStarted) {
            TextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.name)) }
            )

            TextField(
                value = cardNumber,
                onValueChange = onCardNumberChange,
                label = { Text(stringResource(R.string.card_number)) }
            )

            Button(onClick = onStartGame, enabled = name.isNotBlank() && cardNumber.isNotBlank()) {
                Text(stringResource(R.string.start_game))
            }
        } else {
            TextField(
                value = guess,
                onValueChange = onGuessChange,
                label = { Text(stringResource(R.string.guess)) }
            )

            Button(onClick = onSubmitGuess) {
                Text(stringResource(R.string.submit_guess))
            }
        }
        Text(text = response)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenViewModel(
    modifier : Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val name by gameViewModel.name.collectAsStateWithLifecycle()
    val cardNumber by gameViewModel.cardNumber.collectAsStateWithLifecycle()
    val guess by gameViewModel.guess.collectAsStateWithLifecycle()
    val response by gameViewModel.response.collectAsStateWithLifecycle()
    val gameStarted by gameViewModel.gameStarted.collectAsStateWithLifecycle()

    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.new_game))
                            },
                            onClick = {
                                menuExpanded = false
                                gameViewModel.newGame()
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        GameScreen(
            name = name,
            onNameChange = { gameViewModel.updateName(it) },
            cardNumber = cardNumber,
            onCardNumberChange = { gameViewModel.updateCard(it) },
            guess = guess,
            onGuessChange = { gameViewModel.updateGuess(it) },
            onStartGame = { gameViewModel.startGame() },
            response = response,
            onSubmitGuess = { gameViewModel.submitGuess() },
            gameStarted = gameStarted,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    Oving5Theme {
        GameScreen(
            name = "",
            onNameChange = {},
            cardNumber = "",
            onCardNumberChange = {},
            guess = "",
            onGuessChange = {},
            onStartGame = {},
            response = "",
            onSubmitGuess = {},
            gameStarted = false
        )
    }
}
