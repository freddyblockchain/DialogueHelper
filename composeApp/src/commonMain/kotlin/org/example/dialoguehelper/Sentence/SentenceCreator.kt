package org.example.dialoguehelper.Sentence

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog


import androidx.compose.foundation.layout.*
import androidx.compose.material.* // For Material 2 components
// Or: import androidx.compose.material3.* for Material 3
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Import your Sentence and Character models
import org.example.dialoguehelper.Models.Sentence
import org.example.dialoguehelper.Models.Character // Assuming Character has id and name

// --- For Material 3 Dropdown (if you switch) ---
// import androidx.compose.material3.DropdownMenuItem
// import androidx.compose.material3.ExposedDropdownMenuBox
// import androidx.compose.material3.ExposedDropdownMenuDefaults
// --- End Material 3 Dropdown imports ---


/**
 * A form composable for creating a new Sentence.
 *
 * @param availableCharacters The list of characters the user can choose from.
 * @param onSentenceCreated Lambda called when the "Create" button is clicked and inputs are valid,
 *                          passing the newly created Sentence.
 * @param onCancel Lambda called when the "Cancel" button is clicked or form is dismissed.
 */
@Composable
fun CreateNewSentenceForm(
    availableCharacters: List<Character>,
    onSentenceCreated: (Sentence) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var sentenceTitle by rememberSaveable { mutableStateOf("") }
    var sentenceText by rememberSaveable { mutableStateOf("") }

    var selectedCharacter: Character? by remember { mutableStateOf(null) }
    var characterDropdownExpanded by remember { mutableStateOf(false) }

    // Initial character selection (e.g., first in list if available)
    LaunchedEffect(availableCharacters) {
        if (selectedCharacter == null && availableCharacters.isNotEmpty()) {
            //selectedCharacter = availableCharacters.first()
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Create New Sentence",
            style = MaterialTheme.typography.bodyLarge// M3: MaterialTheme.typography.titleLarge
        )

        // Sentence Title TextField
        OutlinedTextField( // M3: androidx.compose.material3.OutlinedTextField
            value = sentenceTitle,
            onValueChange = { sentenceTitle = it },
            label = { Text("Sentence Title (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Character Selection Dropdown (using Material 2 DropdownMenu as an example)
        // For Material 3, ExposedDropdownMenuBox is preferred.
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton( // This acts as the dropdown anchor
                onClick = { characterDropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedCharacter?.name ?: "Select Character")
                // Icon(Icons.Default.ArrowDropDown, "Dropdown arrow") // Optional arrow
            }
            DropdownMenu(
                expanded = characterDropdownExpanded,
                onDismissRequest = { characterDropdownExpanded = false },
                modifier = Modifier.fillMaxWidth() // Adjust width as needed
            ) {
                availableCharacters.forEach { character ->
                    DropdownMenuItem(text = {Text(character.name)}, onClick = {
                        selectedCharacter = character
                        characterDropdownExpanded = false
                    })
                }
            }
        }

        // Sentence Text TextField
        OutlinedTextField( // M3: androidx.compose.material3.OutlinedTextField
            value = sentenceText,
            onValueChange = { sentenceText = it },
            label = { Text("Sentence Text") },
            modifier = Modifier.fillMaxWidth().height(120.dp), // Multi-line
            maxLines = 5
        )

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancel) { // M3: material3.TextButton
                Text("Cancel")
            }
            Spacer(Modifier.width(8.dp))
            Button( // M3: material3.Button
                onClick = {
                    val currentSelectedCharacter = selectedCharacter
                    if (sentenceText.isNotBlank() && currentSelectedCharacter != null) {
                        val newSentence = Sentence(
                            name = sentenceTitle.ifBlank { "Untitled Sentence" }, // Provide default if blank
                            character = currentSelectedCharacter,
                            text = sentenceText
                        )
                        onSentenceCreated(newSentence)
                    }
                },
                // Enable button only if text is entered and a character is selected
                enabled = sentenceText.isNotBlank() && selectedCharacter != null
            ) {
                Text("Create")
            }
        }
    }
}

// How you might use this form inside a Dialog (similar to CreateNewCharacterDialog)
@Composable
fun CreateNewSentenceDialog(
    availableCharacters: List<Character>,
    onDismissRequest: () -> Unit,
    onSentenceCreated: (Sentence) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .widthIn(min = 300.dp, max = 500.dp) // Responsive width
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium, // M3: MaterialTheme.shapes.large
            tonalElevation = 8.dp // M3: tonalElevation = 4.dp
        ) {
            CreateNewSentenceForm(
                availableCharacters = availableCharacters,
                onSentenceCreated = { sentence ->
                    onSentenceCreated(sentence) // Pass it up
                    onDismissRequest() // Optionally close dialog after creation
                },
                onCancel = onDismissRequest,
                modifier = Modifier.padding(bottom = 8.dp) // Add some padding if Surface is tight
            )
        }
    }
}

// --- Optional Preview (make sure Character and Sentence are defined for preview) ---
// @Preview(showBackground = true)
// @Composable
// fun CreateNewSentenceFormPreview() {
//     val sampleCharacters = listOf(
//         Character(id="1", name="Alice", colorHex = "#FF0000"),
//         Character(id="2", name="Bob", colorHex = "#0000FF"),
//         Character(id="3", name="Charlie")
//     )
//     MaterialTheme { // Use your app's theme
//         Surface(modifier = Modifier.fillMaxSize()) { // Provide a background for the preview
//             Box(contentAlignment = Alignment.Center) { // Center the form for better preview
//                 CreateNewSentenceForm(
//                     availableCharacters = sampleCharacters,
//                     onSentenceCreated = { println("Sentence Created: $it") },
//                     onCancel = { println("Sentence Creation Cancelled") },
//                     modifier = Modifier.width(350.dp) // Simulate dialog width
//                 )
//             }
//         }
//     }
// }

// @Preview(showBackground = true)
// @Composable
// fun CreateNewSentenceDialogPreview() {
//      val sampleCharacters = listOf(
//         Character(id="1", name="Alice", colorHex = "#FF0000"),
//         Character(id="2", name="Bob", colorHex = "#0000FF")
//     )
//     MaterialTheme {
//         CreateNewSentenceDialog(
//             availableCharacters = sampleCharacters,
//             onDismissRequest = {},
//             onSentenceCreated = {}
//         )
//     }
// }