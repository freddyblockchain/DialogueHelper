package org.example.dialoguehelper.Providers

import org.example.dialoguehelper.Models.SaveData

interface SaveProvider {
    val filePath: String
    suspend fun readText(): String
    suspend fun writeText(content: SaveData)
}