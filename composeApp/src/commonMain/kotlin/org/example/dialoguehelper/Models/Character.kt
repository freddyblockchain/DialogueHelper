package org.example.dialoguehelper.Models

import kotlinx.serialization.Serializable

@Serializable
data class Character(val name: String)

val something = Character("Frederik")
val something2 = Character("Daniel")
