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
import org.example.dialoguehelper.Navigation.AppNavHost
import org.example.dialoguehelper.Navigation.startScreen
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

    MaterialTheme {
            AppNavHost(navController)
        }
    }

suspend fun updateSaveFile(sentences: List<Sentence>, characters: List<Character>) {
    val saveData = SaveData()
    saveData.sentences = sentences
    saveData.characters = characters

    writeSaveDataToFile(saveData)
}