package org.example.dialoguehelper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.example.dialoguehelper.Models.SaveData
import org.example.dialoguehelper.Providers.SaveProvider
import java.io.File

class SaveProvider : SaveProvider {
    override val filePath: String by lazy {
        val userHome = System.getProperty("user.home")
        val appDir = File(userHome, "DialogueHelper")

        if (!appDir.exists()) {
            appDir.mkdirs()
        }

        val file = File(appDir, "savedata.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.absolutePath
    }

   override suspend fun readText(): String {
        return try {
            val file = File(filePath)

            val jsonString = withContext(Dispatchers.IO) {
                file.readText()
            }

            return jsonString
        } catch (e: Exception) {
            println("Error reading SaveData from file: ${e.message}")
            e.printStackTrace()
            return ""
        }
    }
    override suspend fun writeText(saveData: SaveData){
        val text = Json.encodeToString(saveData)

        val file = File(filePath)
        withContext(Dispatchers.IO){
            file.writeText(text)
        }

    }
}