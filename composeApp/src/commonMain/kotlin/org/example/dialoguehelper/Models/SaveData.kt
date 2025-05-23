package org.example.dialoguehelper.Models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class SaveData {
    var characters: List<Character> = listOf()
    var sentences: List<Sentence> = listOf()
}
fun serializeSaveData(saveData: SaveData): String? {
    return try {
        Json.encodeToString(saveData)
    } catch (e: Exception) {
        // Log the error or handle it as appropriate for your application
        println("Error serializing SaveData: ${e.message}")
        null
    }
}

fun deserializeSaveData(jsonString: String): SaveData {
    if (jsonString.isNullOrBlank()) {
        return SaveData()
    }
    return try {
        Json.decodeFromString<SaveData>(jsonString)
    } catch (e: Exception) {
        // Log the error or handle it as appropriate for your application
        println("Error deserializing SaveData: ${e.message}")
        SaveData()
    }
}
