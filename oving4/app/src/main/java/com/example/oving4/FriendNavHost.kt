package com.example.oving4

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object FriendList

@Serializable
object AddFriend

@Serializable
data class FriendDetail(val index: Int)

@Serializable
data class EditFriend(val index: Int)
@Composable
fun FriendNavHost(
    modifier: Modifier = Modifier,
    viewModel: FriendViewModel = viewModel(),
    ) {
    val nav = rememberNavController()
    val friends by viewModel.friends.collectAsStateWithLifecycle()

    NavHost(navController = nav, startDestination = FriendList, modifier = modifier) {
        composable<FriendList> {
            FriendListScreen(
                friends = friends,
                onFriendClick = { index -> nav.navigate(FriendDetail(index)) },
                onAddClick = { nav.navigate(AddFriend) },
            )
        }
        composable<AddFriend> {
            AddFriendScreen(viewModel = viewModel, onDone = {
                nav.popBackStack() })
        }
        composable<FriendDetail> { entry ->
            val route: FriendDetail = entry.toRoute()
            val friend = friends[route.index]
            FriendDetailScreen(friend = friend,
                onEdit = {
                    viewModel.loadFriend(route.index)
                    nav.navigate(EditFriend(route.index))
                },
                onBack = {
                        nav.popBackStack()
                }
            )
        }
        composable<EditFriend> { entry ->
            val route: EditFriend = entry.toRoute()

            EditFriendScreen(
                viewModel = viewModel,
                index = route.index,
                onDone = {
                    nav.popBackStack()
                }
            )
        }
    }
}