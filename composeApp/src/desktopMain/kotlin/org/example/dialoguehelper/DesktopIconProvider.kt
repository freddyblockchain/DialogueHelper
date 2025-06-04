package org.example.dialoguehelper

// In desktopMain
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.dialoguehelper.Providers.IconProvider

class DesktopIconProvider : IconProvider {
    override fun addIcon(): ImageVector = Icons.Filled.Add
    override fun addIconFilled(): ImageVector {
        return Icons.Outlined.Cancel
    }

    override fun backIcon(): ImageVector {
        return Icons.AutoMirrored.Outlined.ArrowBack
    }
}