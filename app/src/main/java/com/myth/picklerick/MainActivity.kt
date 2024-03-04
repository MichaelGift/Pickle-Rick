package com.myth.picklerick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myth.network.KtorClient
import com.myth.picklerick.screens.CharacterDetailsScreen
import com.myth.picklerick.ui.theme.PickleRickTheme
import com.myth.picklerick.ui.theme.RickAction
import com.myth.picklerick.ui.theme.RickPrimary

class MainActivity : ComponentActivity() {
    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PickleRickTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = RickPrimary
                ) {
                    NavHost(navController = navController, startDestination = "character_details") {
                        composable("character_details") {
                            CharacterDetailsScreen(ktorClient = ktorClient, characterId = 6) {
                                navController.navigate("character_episodes/$it")
                            }
                        }
                        composable(
                            "character_episodes/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val characterId: Int =
                                backStackEntry.arguments?.getInt("characterId") ?: 1
                            CharacterEpisodeScreen(characterId = characterId)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterEpisodeScreen(characterId: Int) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Character episode screen: $characterId", fontSize = 28.sp, color = RickAction)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PickleRickTheme {
        Greeting("Android")
    }
}