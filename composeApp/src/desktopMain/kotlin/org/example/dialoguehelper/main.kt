package org.example.dialoguehelper

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DialogueHelper",
        state = WindowState(WindowPlacement.Maximized)
    ) {
        App(DesktopIconProvider(), SaveProvider())
    }
}