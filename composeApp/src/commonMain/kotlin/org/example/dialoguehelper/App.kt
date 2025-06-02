package org.example.dialoguehelper

import ProviderManager
import org.example.dialoguehelper.Saving.readSaveDataFromFile
import org.example.dialoguehelper.Saving.writeSaveDataToFile
import org.example.dialoguehelper.Sentence.CreateNewSentenceDialog
import org.example.dialoguehelper.Sentence.SentenceView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.dialoguehelper.Managers.DataManager.Companion.characters
import org.example.dialoguehelper.Managers.DataManager.Companion.sentences
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.dialoguehelper.Models.Character
import org.example.dialoguehelper.Models.SaveData
import org.example.dialoguehelper.Models.Sentence
import org.example.dialoguehelper.Providers.IconProvider
import org.example.dialoguehelper.Providers.SaveProvider

fun getHomeIcon(iconProvider: IconProvider): ImageVector {
    return iconProvider.addIcon()
}
@Composable
@Preview
fun App(iconProvider: IconProvider, saveProvider: SaveProvider) {
    ProviderManager.saveProvider = saveProvider
    ProviderManager.iconProvider = iconProvider

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        val saveData = readSaveDataFromFile() // suspend function
        characters.addAll(saveData.characters) // assuming saveData is a List<Character>
        sentences.addAll(saveData.sentences)
    }
    MaterialTheme {
        var isToggled by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                CharactersView(characters, {})
            }
            if (!isToggled) {

                FloatingActionButton(
                    onClick = { isToggled = !isToggled },
                ) {
                    Icon(
                        imageVector = iconProvider.addIcon(), // Assuming this returns an ImageVector
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

            var newSentenceToggle by remember { mutableStateOf(false) }

            Button(onClick = { newSentenceToggle = !newSentenceToggle }) {
                Text("Create New Sentence")
            }

            if (newSentenceToggle) {
                CreateNewSentenceDialog(
                    onDismissRequest = { newSentenceToggle = false },
                    onSentenceCreated = { newSentence ->
                        sentences.add(newSentence)

                        // Launch coroutine to save data asynchronously
                        coroutineScope.launch {
                            updateSaveFile(sentences, characters)
                        }
                    },
                    availableCharacters = characters
                )
            }


            LazyColumn {
                items(sentences) { sentence ->
                    SentenceView(sentence)
                }
            }
        }
    }
}

suspend fun updateSaveFile(sentences: List<Sentence>, characters: List<Character>) {
    val saveData = SaveData()
    saveData.sentences = sentences
    saveData.characters = characters

    writeSaveDataToFile(saveData)
}