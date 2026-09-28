package com.example.oving4

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

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
fun FriendAdder(
    name: String,
    birthDate: String,
    onNameChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onAdd: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.title),
            modifier = modifier.padding(8.dp),
            )

        NameField(value = name, onValueChange = onNameChange)
        BirthDateField(value = birthDate, onValueChange = onBirthDateChange)

        Button(onClick = onAdd ) {
            Text(text = buttonText)
        }
    }
}

@Composable
fun AddFriendScreen(
    viewModel: FriendViewModel,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val friends by viewModel.friends.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val birthDate by viewModel.birthDate.collectAsStateWithLifecycle()

    FriendAdder(
        name = name,
        birthDate = birthDate,
        onNameChange = { viewModel.changeName(it) },
        onBirthDateChange = { viewModel.changeBirthday(it) },
        onAdd = {
            viewModel.addFriend()
            onDone()
        },
        buttonText = stringResource(R.string.add),
        modifier = modifier,
    )
}

@Composable
fun EditFriendScreen(
    viewModel: FriendViewModel,
    index: Int,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val name by viewModel.name.collectAsStateWithLifecycle()
    val birthDate by viewModel.birthDate.collectAsStateWithLifecycle()

    FriendAdder(
        name = name,
        birthDate = birthDate,
        onNameChange = { viewModel.changeName(it) },
        onBirthDateChange = { viewModel.changeBirthday(it) },
        onAdd = {
            viewModel.updateFriend(index)
            onDone()
        },
        buttonText = stringResource(R.string.save),
        modifier = modifier,
    )
}

@Composable
fun FriendListScreen(
    friends: List<Friend>,
    onFriendClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {

        Button(onClick = onAddClick) {
            Text(text = stringResource(R.string.add))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(friends.size) { index ->
                FriendItem(
                    friend = friends[index],
                    modifier = Modifier.clickable {
                        onFriendClick(index)
                    }
                )
            }
        }
    }
}

@Composable
fun FriendItem(
    friend: Friend,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = friend.name)
        Text(text = friend.birthDate)
    }
}

@Composable
fun FriendDetailScreen(
    friend: Friend,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {
        Text(text = friend.name)
        Text(text = friend.birthDate)

        Button(onClick = onEdit) {
            Text(text = stringResource(R.string.edit))
        }

        Button(onClick = onBack) {
            Text(text = stringResource(R.string.back))
        }
    }
}

@Composable
fun FriendScreenWithViewModel(
    modifier: Modifier = Modifier,
    viewModel: FriendViewModel = viewModel(),
) {
    val friends by viewModel.friends.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val birthDate by viewModel.birthDate.collectAsStateWithLifecycle()

    FriendAdder(
        name = name,
        birthDate = birthDate,
        onNameChange = { viewModel.changeName(it) },
        onBirthDateChange = { viewModel.changeBirthday(it) },
        onAdd = { viewModel.addFriend() },
        buttonText = stringResource(R.string.add),
        modifier = modifier,
    )
}


@Preview(showBackground = true)
@Composable
fun AddFriendPreview() {
    FriendAdder(
        name = "",
        birthDate = "",
        onNameChange = {},
        onBirthDateChange = {},
        onAdd = {},
        buttonText = "Legg til"
    )
}

