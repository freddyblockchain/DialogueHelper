package org.example.dialoguehelper.Sentence

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.dialoguehelper.Models.Sentence
import kotlin.text.isNotBlank


@Composable
fun SentenceView(sentence: Sentence, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Optional: Display Sentence Title if it's important for the view
                Text(
                    text = sentence.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Character Name (could be styled with character's color)
                Text(
                    text = "${sentence.character.name}:", // Accessing name from Character object
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 8.dp)
                )

                // Sentence Text
                Text(
                    text = "\"${sentence.text}\"",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic // Dialogue is often italicized
                )
            }

            // Optional: Display Sentence ID (for debugging or admin views)
            // Text(
            //     text = "ID: ${sentence.id}",
            //     style = MaterialTheme.typography.labelSmall,
            //     fontSize = 10.sp,
            //     color = MaterialTheme.colorScheme.onSurfaceVariant,
            //     modifier = Modifier.padding(top = 8.dp)
            // )
        }
    }
}