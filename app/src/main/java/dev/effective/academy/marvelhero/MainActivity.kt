package dev.effective.academy.marvelhero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.effective.academy.marvelhero.ui.theme.MarvelHeroTheme

val heros = listOf<Heros>(
    Heros.DEADPOOL,
    Heros.IRON_MAN,
    Heros.SPIDER_MAN
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHeroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) {
                            MainScreen(navController)
                        }
                        composable(
                            route = Routes.HeroScreen.route,
                            arguments = listOf(navArgument("hero") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val heroName = backStackEntry.arguments?.getString("hero")
                            val hero = heros.find { it.name == heroName }
                            if (hero != null) {
                                HeroScreen(navController, hero)
                            } else {
                                Text("Hero not found")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun GreetingPreview() {
    MarvelHeroTheme {
        Greeting("Android")
    }
}

//@Preview(showBackground = true)
//@PreviewParameter(rememberNavController():)
@Composable
fun MainScreen(navController: NavHostController) {

//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = Routes.Home.route) {
//            composable(Routes.Home.route) {
//                MainScreen(navController)
//            }
//            composable(
//                route = Routes.HeroScreen.route,
//                arguments = listOf(navArgument("hero") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val heroName = backStackEntry.arguments?.getString("hero")
//                val hero = heros.find { it.name == heroName }
//                if (hero != null) {
//                    HeroScreen(hero)
//                } else {
//                    Text("Hero not found")
//                }
//            }
//
//        }


    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 15.dp),horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.marvel), contentDescription = "logo")
            }
            Row(
                Modifier
                    .fillMaxWidth()
                ,horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Choose your hero", color = Color.White, fontSize = 30.sp)
            }
        }

        HeroList(navController = navController)
    }

}









@Composable
fun HeroScreen(navController: NavController,hero: Heros) {
    println("hero name: ${hero.name}")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {

        Image(
            painter = painterResource(id = R.drawable.ic_main_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = hero.link),
            modifier = Modifier.fillMaxSize(),
            contentDescription = hero.name,
            contentScale = ContentScale.Crop

        )
        Column() {
            Row {
                Text(text = hero.name.replace('_', ' '), fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,modifier = Modifier.padding(15.dp,30.dp),
                    color = Color.White
                )
            }
            Row {
                Text(text = hero.description, fontSize = 20.sp,
                    modifier = Modifier.padding(15.dp,30.dp), color = Color.White
                )
            }
        }

        TextButton(modifier = Modifier.align(Alignment.TopStart), onClick = {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Home.route)
            }

        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }
    }
}
@Composable
fun HeroList(navController: NavController){
    println("created")
    LazyRow(
        modifier = Modifier
            .fillMaxSize(),
//                .align(AbsoluteAlignment.CenterLeft),
        contentPadding = PaddingValues(20.dp,55.dp,20.dp,20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(heros) { _, hero ->
            TextButton(onClick = {
                println(hero.name)
                navController.navigate("HeroScreen/${hero.name}"){

                }

            }, shape = RoundedCornerShape(CornerSize(20))) {
                Box {
                    Image(
                        modifier =  Modifier.size(300.dp,500.dp),
                        painter = painterResource(id = hero.link),
                        contentDescription = hero.name,
                        contentScale = ContentScale.Crop
                    )
                    Text(text = hero.name.replace('_',' '), modifier = Modifier
                        .width(300.dp)
                        .align(Alignment.BottomStart)
                        .padding(50.dp, 80.dp)
                        .fillMaxWidth(), color = Color.White,
                        fontSize = 20.sp)
                }


            }

        }
    }
}