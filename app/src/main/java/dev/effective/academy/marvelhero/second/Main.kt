package dev.effective.academy.marvelhero.second

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.effective.academy.marvelhero.Heros

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object HeroScreen : Routes("HeroScreen/{hero}")
}
class Main {

    val heros = listOf(
        Heros.DEADPOOL,
        Heros.IRON_MAN,
        Heros.SPIDER_MAN
    )
    @Preview
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(Routes.Home.route) {
                HeroList(navController)
            }
            composable(
                route = Routes.HeroScreen.route,
                arguments = listOf(navArgument("hero") { type = NavType.StringType })
            ) { backStackEntry ->
                val heroName = backStackEntry.arguments?.getString("hero")
                val hero = heros.find { it.name == heroName }
                HeroScreen(hero)
            }
        }
    }

    @Composable
    fun HeroList(navController: NavController) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            itemsIndexed(heros) { _, hero ->
                TextButton(onClick = {
                    navController.navigate("HeroScreen/${hero.name}")
                }) {
                    Text(text = hero.name)
                }
            }
        }
    }

    @Composable
    fun HeroScreen(hero: Heros?) {
        if (hero == null) {
            Text(text = "Hero not found")
            return
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = hero.name)
        }
    }
}
