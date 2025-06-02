package org.example.dialoguehelper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Use this for better type safety and features
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.dialoguehelper.Models.Character


@Composable
fun CharactersView(
    characters: SnapshotStateList<Character>,
    onCharacterClick: (Character) -> Unit, // Callback for when a character is clicked
) {
    // Determine a width for the character list panel
    val listWidth = 200.dp // You can adjust this

    LazyColumn(
        modifier = Modifier
            .width(listWidth)
            .padding(8.dp), // Instead of contentPadding
        horizontalAlignment = Alignment.Start
    ) {
        items(characters) { character ->
            CharacterItem(
                character = character,
                onClick = { onCharacterClick(character) }
            )
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}