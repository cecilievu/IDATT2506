package com.example.oving4

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FriendViewModel : ViewModel() {

    private val _friends = MutableStateFlow(listOf<Friend>())
    val friends: StateFlow<List<Friend>> = _friends.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    fun changeName(value: String) {
        _name.value = value
    }
    fun changeBirthday(value: String) {
        _birthDate.value = value
    }
    fun addFriend() {
        val friend = Friend(name = _name.value, birthDate =
            _birthDate.value)
        _friends.value = _friends.value + friend
        _name.value = ""
        _birthDate.value = ""
    }

    fun loadFriend(index: Int) {
        val friend = _friends.value[index]

        _name.value = friend.name
        _birthDate.value = friend.birthDate
    }

    fun updateFriend(index: Int) {
        val updatedFriend = Friend(
            name = _name.value,
            birthDate = _birthDate.value
        )

        _friends.value = _friends.value.toMutableList().also {
            it[index] = updatedFriend
        }

        _name.value = ""
        _birthDate.value = ""
    }
}