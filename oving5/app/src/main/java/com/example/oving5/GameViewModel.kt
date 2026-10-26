package com.example.oving5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _cardNumber = MutableStateFlow("")
    val cardNumber: StateFlow<String> = _cardNumber.asStateFlow()

    private val _guess = MutableStateFlow("")
    val guess: StateFlow<String> = _guess.asStateFlow()

    private val httpWrapper = HttpWrapper("https://bigdata.idi.ntnu.no/mobil/tallspill.jsp")

    private val _response = MutableStateFlow("")
    val response: StateFlow<String> = _response.asStateFlow()

    private val _gameStarted = MutableStateFlow(false)
    val gameStarted: StateFlow<Boolean> = _gameStarted.asStateFlow()

    fun updateName(newName : String) {
        _name.value = newName
    }

    fun updateCard(newCardNumber : String) {
        _cardNumber.value = newCardNumber
    }

    fun updateGuess(newGuess : String) {
        _guess.value = newGuess
    }

    fun startGame() {
        if (_name.value.isBlank() || _cardNumber.value.isBlank()) {
            return
        }

        viewModelScope.launch {
            val parameters = mapOf(
                "navn" to _name.value,
                "kortnummer" to _cardNumber.value
            )

            val response = httpWrapper.get(parameters)
            _response.value = response
            _gameStarted.value = true
        }
    }

    fun submitGuess() {
        viewModelScope.launch {
            val parameters = mapOf(
                "tall" to _guess.value
            )
            val response = httpWrapper.get(parameters)
            _response.value = response
        }
    }

    fun newGame() {
        _gameStarted.value = false
        _name.value = ""
        _cardNumber.value = ""
        _guess.value = ""
        _response.value = ""
    }
}
