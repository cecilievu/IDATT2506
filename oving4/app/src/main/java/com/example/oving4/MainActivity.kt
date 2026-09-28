package com.example.oving4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oving4.FriendAdder
import com.example.oving4.FriendScreenWithViewModel
import com.example.oving4.ui.theme.Oving4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Oving4Theme {
                Scaffold { innerPadding ->
                    FriendNavHost(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}