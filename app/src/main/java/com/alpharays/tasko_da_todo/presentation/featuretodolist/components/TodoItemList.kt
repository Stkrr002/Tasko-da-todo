package com.alpharays.tasko_da_todo.presentation.featuretodolist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alpharays.tasko_da_todo.domain.model.TaskData
import com.alpharays.tasko_da_todo.presentation.dragdrop.DragDropColumn
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

@Composable
fun TodoItemList(
    itemsStateFlow: StateFlow<List<TaskData>>,
    onItemClicked: (TaskData) -> Unit = {},
    onSwapItems: (Int, Int) -> Unit,
    onDelete: (TaskData) -> Unit
) {
    DragDropColumn(
        items = itemsStateFlow.collectAsState().value,
        onSwap = onSwapItems
    ) { item ->
        val randomColor = Color(
            red = Random(item.id).nextInt(256),
            green = Random(item.id + 1).nextInt(256),
            blue = Random(item.id + 2).nextInt(256)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked(item) },
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = randomColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.title.replaceFirst(
                            oldChar = item.title.first(),
                            newChar = item.title.first().uppercaseChar()
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.description.replaceFirst(
                            oldChar = item.description.first(),
                            newChar = item.description.first().uppercaseChar()
                        ),
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                }
                IconButton(
                    onClick = { onDelete(item) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        }
    }
}