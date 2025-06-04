package org.example.dialoguehelper.Screens

import ProviderManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.dialoguehelper.CharactersView
import org.example.dialoguehelper.CreateNewCharacterDialog
import org.example.dialoguehelper.Managers.DataManager
import org.example.dialoguehelper.Managers.DataManager.Companion.characters
import org.example.dialoguehelper.Managers.DataManager.Companion.sentences
import org.example.dialoguehelper.Navigation.characterScreen
import org.example.dialoguehelper.Saving.readSaveDataFromFile
import org.example.dialoguehelper.updateSaveFile

@Composable
fun CharacterOverviewScreen(navController: NavController) {
    var isToggled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val saveData = readSaveDataFromFile() // suspend function
        characters.clear()
        characters.addAll(saveData.characters) // assuming saveData is a List<Character>
        sentences.addAll(saveData.sentences)
    }
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            CharactersView(DataManager.characters, {navController.navigate(it)})
        }
        if (!isToggled) {

            FloatingActionButton(
                onClick = { isToggled = !isToggled },
            ) {
                Icon(
                    imageVector = ProviderManager.iconProvider.addIcon(), // Assuming this returns an ImageVector
                    contentDescription = "Add Character" // Provide a meaningful description
                )
            }
        }
        val coroutineScope = rememberCoroutineScope()

        if (isToggled) {
            CreateNewCharacterDialog(
                onDismissRequest = {
                    isToggled = false
                },
                onCharacterCreated = { newCharacter ->
                    isToggled = false
                    characters.add(newCharacter)

                    coroutineScope.launch(Dispatchers.IO) {
                        updateSaveFile(sentences, characters)
                    }
                }
            )
        }
    }
}
