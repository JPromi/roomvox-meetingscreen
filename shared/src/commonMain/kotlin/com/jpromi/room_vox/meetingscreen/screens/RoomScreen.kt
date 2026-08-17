package com.jpromi.room_vox.meetingscreen.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoomScreen(
    onOpenHome: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.weight(4f).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Datetime
            Column {
                Text(text = "14:33", modifier = Modifier.padding(bottom = 4.dp), fontWeight = FontWeight.W500, fontSize = 50.sp)
                Text("31.01.2026", fontWeight = FontWeight.W400, fontSize = 20.sp)
            }

            // Name & Status
            Column {
                Text("Lehrsaal", modifier = Modifier.padding(bottom = 4.dp), fontWeight = FontWeight.W500, fontSize = 30.sp)

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(24.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Text("Frei")
                }
            }

            // Settings
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(onClick = onOpenHome) {
                    Text("Home")
                }
            }
        }

        Column(modifier = Modifier.weight(3f)) {}
    }
}