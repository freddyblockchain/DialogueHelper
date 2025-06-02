package org.example.dialoguehelper.Saving

import ProviderManager.Companion.saveProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.dialoguehelper.Models.SaveData
import org.example.dialoguehelper.Models.deserializeSaveData

const val SAVE_FILE_NAME = "savedata.json" // Already defined but ensure consistency

suspend fun writeSaveDataToFile(saveData: SaveData): Boolean {
    saveProvider.writeText(saveData)

    return true
}

suspend fun readSaveDataFromFile(): SaveData {
    val saveDataAsString = saveProvider.readText()

    val saveData = deserializeSaveData(saveDataAsString)

    return saveData
}