package org.example.dialoguehelper

import Saving.readSaveDataFromFile
import Saving.writeSaveDataToFile
import Sentence.CreateNewSentenceDialog
import Sentence.SentenceView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.dialoguehelper.Models.Character
import org.example.dialoguehelper.Models.SaveData
import org.example.dialoguehelper.Models.Sentence
import org.example.dialoguehelper.Providers.IconProvider
import org.example.dialoguehelper.Providers.SaveProvider

fun getHomeIcon(iconProvider: IconProvider): ImageVector {
    return iconProvider.addIcon()
}

lateinit var saveProvider: SaveProvider

@Composable
@Preview
fun App(iconProvider: IconProvider, saveProvider: SaveProvider) {
    org.example.dialoguehelper.saveProvider = saveProvider
    val openAlertDialog = remember { mutableStateOf(false) }

    val characters = remember { mutableStateListOf<Character>() }
    val sentences = remember { mutableStateListOf<Sentence>() }

    LaunchedEffect(Unit) {
        val saveData = readSaveDataFromFile() // suspend function
        characters.addAll(saveData.characters) // assuming saveData is a List<Character>
        sentences.addAll(saveData.sentences)
    }
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        // isToggled initial value should be read from a view model or persistent storage.
        var isToggled by remember { mutableStateOf(false) }

        var newSentenceToggle by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            LazyColumn {
                for(character in characters){
                    item() {
                        Text(character.name)
                    }
                }
            }
            if(!isToggled){
                IconButton(
                    onClick = { isToggled = !isToggled }
                ) {
                    Icon(
                        imageVector = if (isToggled) {
                            iconProvider.addIcon()
                        } else {
                            iconProvider.addIconFilled()
                        },
                        contentDescription = if (isToggled) "Selected icon button" else "Unselected icon button."
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

            Button(onClick = {newSentenceToggle = !newSentenceToggle } ){
                Text("Create New Sentence")
            }

            if(newSentenceToggle){
                CreateNewSentenceDialog(onDismissRequest = {newSentenceToggle = false},  onSentenceCreated = { newSentence ->
                    sentences.add(newSentence)

                    // Launch coroutine to save data asynchronously
                    coroutineScope.launch {
                        updateSaveFile(sentences, characters)
                    }
                }, availableCharacters = characters)
            }


            LazyColumn {
                items(sentences) { sentence ->
                    SentenceView(sentence)
                }
            }
        }
    }
}

suspend fun updateSaveFile(sentences: List<Sentence>, characters: List<Character>){
    val saveData = SaveData()
    saveData.sentences = sentences
    saveData.characters = characters

    writeSaveDataToFile(saveData)
}