package com.example.oving2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oving2.ui.theme.Oving2Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oving2Theme {
                val context = LocalContext.current
                val message = stringResource(R.string.info_message)
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    RecipeScreen(
                        recipes = sampleRecipes,
                        onRecipeClick = { recipe ->
                            Toast.makeText(context, recipe.title,
                                Toast.LENGTH_SHORT).show()
                        },
                        onInfoClick = {
                            Toast.makeText(context, message,
                                Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

// hele skjermen med header, oppskriftsliste og knapp nederst
@Composable
fun RecipeScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
        .background(MaterialTheme.colorScheme.primary)
) {
    Column(modifier = modifier.fillMaxSize()) {
        RecipeHeader()
        ClickableRecipeList(
            recipes = recipes,
            onRecipeClick = onRecipeClick,
            modifier = Modifier.weight(1f),
        )
        Button(
            onClick = onInfoClick,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            )
        ) {
            Text(text = stringResource(R.string.show_info))
        }
    }
}

// viser klikkbar liste med oppskrifter
@Composable
fun ClickableRecipeList (
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
       items(recipes) { recipe ->
           RecipeListItem(
               recipe = recipe,
               modifier = Modifier.clickable{ onRecipeClick(recipe) },
           )
       }
    }
}

// header med tittel høst og ukens oppskrifter
@Composable
fun RecipeHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(160.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.header),
            contentDescription = stringResource(R.string.header_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            text = stringResource(R.string.screen_title1),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(15.dp)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(10.dp)
        )
        Text(
            text = stringResource(R.string.screen_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(10.dp)

        )
    }
}

// viser en item med tittel, kategori og tid
@Composable
fun RecipeListItem(recipe: Recipe, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(15.dp)

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
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    Oving2Theme {
        RecipeScreen(
            recipes = sampleRecipes,
            onRecipeClick = {},
            onInfoClick = {},
        )
    }
}