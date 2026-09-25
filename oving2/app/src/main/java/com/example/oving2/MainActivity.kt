package com.example.oving2

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oving2.ui.theme.Oving2Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

data class Recipe(
    val title: String,
    val category: String,
    val time: String,
)

val sampleRecipes: List<Recipe> = listOf(
    Recipe("Fiskesuppe", "Hovedrett", "35 min"),
    Recipe("Lapskaus", "Hovedrett", "90 min"),
    Recipe("Vaffelrøre", "Dessert", "20 min"),
    Recipe("Tomatsuppe", "Forrett", "25 min"),
    Recipe("Eplekake", "Dessert", "55 min"),
    Recipe("Fiskekaker", "Hovedrett", "40 min"),
)

class MainActivity : ComponentActivity() {

    }

@Composable
fun RecipeListItem(recipe: Recipe, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().padding(16.dp),
    ) {
        Column {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = recipe.category,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            text = recipe.time,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun RecipeList(recipe: List<Recipe>, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier
    ) {
        items(recipe) { recipe ->
            RecipeListItem(recipe = recipe)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreview() {
    Oving2Theme {
        RecipeList(recipe = sampleRecipes)
    }
}