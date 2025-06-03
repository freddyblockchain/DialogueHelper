package org.example.dialoguehelper.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.dialoguehelper.Screens.CharacterOverviewScreen
import org.example.dialoguehelper.Screens.CharacterSpecificScreen

val startScreen = "STARTSCREEN"
val characterScreen = "CHARACTERSCREEN"
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startScreen,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(startScreen) {
            CharacterOverviewScreen(navController)
        }
        composable(characterScreen) {
            CharacterSpecificScreen(navController)
        }
    }
}