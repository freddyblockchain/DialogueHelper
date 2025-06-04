package org.example.dialoguehelper.Providers

import androidx.compose.ui.graphics.vector.ImageVector

interface IconProvider {
    fun addIcon(): ImageVector
    fun addIconFilled(): ImageVector
    fun backIcon(): ImageVector
}