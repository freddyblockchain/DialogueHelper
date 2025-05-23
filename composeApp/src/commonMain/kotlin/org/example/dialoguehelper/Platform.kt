package org.example.dialoguehelper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform