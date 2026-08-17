package com.jpromi.room_vox.meetingscreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.jpromi.room_vox.meetingscreen.screens.ConfigurationScreen
import com.jpromi.room_vox.meetingscreen.screens.RoomScreen

private enum class Screen {
    Configuration,
    Room
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.Room) }

        when (currentScreen) {
            Screen.Configuration -> ConfigurationScreen(
                onGoBack = { currentScreen = Screen.Room }
            )
            Screen.Room -> RoomScreen(
                onOpenConfiguration = { currentScreen = Screen.Configuration },
            )
        }
    }
}