package org.example.dialoguehelper.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.dialoguehelper.CharactersView

val startScreen = "STARTSCREEN"
val characterScreen = "CHARACTERSCREEN"
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startScreen,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        composable(startScreen) {
        }
        composable(characterScreen) {
            CharacterScreen(navController)
    }
}