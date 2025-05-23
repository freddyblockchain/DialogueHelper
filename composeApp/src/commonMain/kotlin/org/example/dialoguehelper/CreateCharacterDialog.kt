package org.example.dialoguehelper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.dialoguehelper.Models.Character
import kotlin.text.isNotBlank


@Composable
fun CreateNewCharacterDialog(
    onDismissRequest: () -> Unit,
    onCharacterCreated: (Character) -> Unit // Character data class
) {
    var characterName by rememberSaveable { mutableStateOf("") }
    var characterDescription by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .width(400.dp) // Set a preferred width
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium, // Or MaterialTheme.shapes.extraLarge for M3
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Create New Character",
                    style = MaterialTheme.typography.bodyLarge// Or M3 equivalent: Typography.TitleLarge
                )

                OutlinedTextField( // Or material3.OutlinedTextField
                    value = characterName,
                    onValueChange = { characterName = it },
                    label = { Text("Character Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismissRequest) { // Or material3.TextButton
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button( // Or material3.Button
                        onClick = {
                            if (characterName.isNotBlank()) {
                                onCharacterCreated(Character(characterName))
                            }
                        },
                        enabled = characterName.isNotBlank()
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}