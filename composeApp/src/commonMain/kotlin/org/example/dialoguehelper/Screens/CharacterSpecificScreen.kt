package org.example.dialoguehelper.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.dialoguehelper.Models.Character

@Composable
fun CharacterSpecificScreen(
    navController: NavController,
    character: Character // Assuming you're passing a character ID
) {
    Column(
        modifier = Modifier
            .padding(16.dp) // Overall padding for the screen content
    ) {
        // Row for the back button and optional title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Pushes items to ends if there are multiple
        ) {
            IconButton(onClick = {
                navController.popBackStack() // Or navController.navigateUp()
            }) {
                Icon(
                    imageVector = ProviderManager.iconProvider.backIcon(),
                    contentDescription = "Back"
                )
            }

            // Optional: Title text, if you want it next to the button or centered
            // If you want a title, you might adjust the Row's arrangement
            // For example, if title is centered, IconButton could be at the start
            // and an empty Spacer with weight could be used on the other side.
            Text(
                text = "Character: ${character.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f).padding(start = 8.dp) // Allow title to take space
            )
            // If you only want the back button and no title next to it in this Row,
            // you can remove the Text and the horizontalArrangement.SpaceBetween
            // might not be needed or could be Arrangement.Start.
        }

        // Spacer(modifier = Modifier.height(16.dp)) // Space between header and content

        // Your screen content for the specific character goes here
        Text(
            text = "Details for character ID: ${character.name}",
            modifier = Modifier.padding(top = 16.dp) // Add some space above content
        )
        // Example: Display sentences for this character, character stats, etc.
        // You might want to wrap this in a Column if there's more content
        // Column(modifier = Modifier.fillMaxSize()) { ... }
    }
}