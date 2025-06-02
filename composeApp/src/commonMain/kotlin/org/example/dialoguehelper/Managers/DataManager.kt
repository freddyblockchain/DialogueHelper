package org.example.dialoguehelper.Managers

import androidx.compose.runtime.mutableStateListOf
import org.example.dialoguehelper.Models.Character
import org.example.dialoguehelper.Models.Sentence

class DataManager {
    companion object {
        val characters = mutableStateListOf<Character>()
        val sentences = mutableStateListOf<Sentence>()
    }
}