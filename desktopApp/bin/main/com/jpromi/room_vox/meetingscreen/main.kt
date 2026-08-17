package com.jpromi.room_vox.meetingscreen

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RoomVox Meetingscreen",
    ) {
        App()
    }
}