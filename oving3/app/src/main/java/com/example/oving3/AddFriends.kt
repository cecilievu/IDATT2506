package com.example.oving3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Friend(
    val name: String,
    val birthDate: String,
)

@Composable
fun NameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.name)) },
        modifier = modifier,
    )
}

@Composable
fun BirthDateField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.birth)) },
        modifier = modifier,
    )
}

@Composable
fun FriendAdder(modifier: Modifier = Modifier) {
    var name by rememberSaveable() { mutableStateOf("") }
    var birth by rememberSaveable() { mutableStateOf("") }
    var friends by remember() { mutableStateOf(listOf<Friend>()) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.title),
            modifier = modifier.padding(8.dp),
            )

        NameField(value = name, onValueChange = { name = it })
        BirthDateField(value = birth, onValueChange = { birth = it})

        Button(onClick = {
            val friend = Friend(
                name = name,
                birthDate = birth
            )

            friends = friends + friend
            name = ""
            birth = ""
        }) {
            Text(text = stringResource(R.string.add))
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(friends) {friend -> FriendItem(friend = friend) }
        }
    }
}

@Composable
fun FriendItem(friend: Friend, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = friend.name)
        Text(text = friend.birthDate)
    }
}

@Preview(showBackground = true)
@Composable
fun AddFriendPreview() {
    FriendAdder()
}

