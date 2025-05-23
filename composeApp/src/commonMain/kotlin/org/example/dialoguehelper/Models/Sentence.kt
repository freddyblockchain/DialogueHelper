package org.example.dialoguehelper.Models

import kotlinx.serialization.Serializable

@Serializable
data class Sentence(val character: Character, val text: String, val name: String) {
}