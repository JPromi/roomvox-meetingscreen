package com.jpromi.room_vox.meetingscreen.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onOpenConfiguration: () -> Unit,
    onOpenRoom: () -> Unit
) {
    Column {
        Text("Startscreen")

        Button(onClick = onOpenConfiguration) {
            Text("Konfiguration öffnen")
        }

        Button(onClick = onOpenRoom) {
            Text("Raum öffnen")
        }
    }
}