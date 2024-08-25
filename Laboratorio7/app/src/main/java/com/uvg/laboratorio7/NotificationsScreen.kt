package com.uvg.laboratorio7

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.laboratorio7.notifications.Notification
import com.uvg.laboratorio7.notifications.NotificationType
import com.uvg.laboratorio7.notifications.generateFakeNotifications
import com.uvg.laboratorio7.ui.theme.Laboratorio7Theme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(modifier: Modifier = Modifier) {
    var selectedChip by rememberSaveable { mutableStateOf<NotificationType?>(null) }
    val notifications = remember { generateFakeNotifications() }

    val filteredNotifications = if (selectedChip != null) {
        notifications.filter { it.type == selectedChip }
    } else {
        notifications
    }

    val chipLabels = listOf("General", "Nueva Publicación", "Nuevo Mensaje", "Nuevo Like")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Notificaciones") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Return to previous page", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )

            Text(
                text = "Tipos de Notificaciones",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            NotificationChips(
                chipTexts = chipLabels,
                selectedChip = selectedChip,
                onChipSelected = { selectedLabel ->
                    selectedChip = when (selectedLabel) {
                        "General" -> if (selectedChip == NotificationType.GENERAL) null else NotificationType.GENERAL
                        "Nueva Publicación" -> if (selectedChip == NotificationType.NEW_POST) null else NotificationType.NEW_POST
                        "Nuevo Mensaje" -> if (selectedChip == NotificationType.NEW_MESSAGE) null else NotificationType.NEW_MESSAGE
                        "Nuevo Like" -> if (selectedChip == NotificationType.NEW_LIKE) null else NotificationType.NEW_LIKE
                        else -> null
                    }
                },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 32.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp)
            ) {
                NotificationList(notifications = filteredNotifications)
            }
        }
    }
}

@Composable
fun NotificationChips(
    chipTexts: List<String>,
    selectedChip: NotificationType?,
    onChipSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chipTexts.size) { index ->
            val chipLabel = chipTexts[index]
            val isSelected = when (chipLabel) {
                "General" -> selectedChip == NotificationType.GENERAL
                "Nueva Publicación" -> selectedChip == NotificationType.NEW_POST
                "Nuevo Mensaje" -> selectedChip == NotificationType.NEW_MESSAGE
                "Nuevo Like" -> selectedChip == NotificationType.NEW_LIKE
                else -> false
            }
            val chipColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            NotificationType(
                text = chipLabel,
                isSelected = isSelected,
                color = chipColor,
                onClick = {
                    onChipSelected(chipLabel)
                }
            )
        }
    }
}

@Composable
fun NotificationType(
    text: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(8.dp))
            .border(
                border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.secondaryContainer) else BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Icon(
                Icons.Filled.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun NotificationList(notifications: List<Notification>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notifications.size) { index ->
            NotificationItem(notification = notifications[index])
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    val icon = when (notification.type) {
        NotificationType.GENERAL -> Icons.Filled.Notifications
        NotificationType.NEW_POST -> Icons.Filled.Add
        NotificationType.NEW_MESSAGE -> Icons.Filled.Email
        NotificationType.NEW_LIKE -> Icons.Filled.ThumbUp
    }
    val iconBackgroundColor = when (notification.type) {
        NotificationType.GENERAL -> MaterialTheme.colorScheme.primaryContainer
        NotificationType.NEW_POST -> MaterialTheme.colorScheme.secondaryContainer
        NotificationType.NEW_MESSAGE -> MaterialTheme.colorScheme.tertiaryContainer
        NotificationType.NEW_LIKE -> MaterialTheme.colorScheme.primary
    }
    val iconTintColor = when (notification.type) {
        NotificationType.GENERAL -> MaterialTheme.colorScheme.onPrimaryContainer
        NotificationType.NEW_POST -> MaterialTheme.colorScheme.onSecondaryContainer
        NotificationType.NEW_MESSAGE -> MaterialTheme.colorScheme.onTertiaryContainer
        NotificationType.NEW_LIKE -> MaterialTheme.colorScheme.onPrimary
    }

    val dateFormat = SimpleDateFormat("d MMM · h:mm a", Locale.getDefault())
    val formattedDate = dateFormat.format(notification.sendAt)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBackgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                MarqueeText(
                    text = notification.title,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = notification.body,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun MarqueeText(
    text: String,
    modifier: Modifier = Modifier,
    speed: Int =70
) {
    var offset by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            offset += 0.5f
            if (offset > 40f) offset = 0f
            delay(speed.toLong())
        }
    }

    Text(
        text = text,
        modifier = modifier.graphicsLayer(translationX = offset),
        maxLines = 1,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    Laboratorio7Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NotificationScreen()
        }
    }
}
