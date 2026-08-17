package com.jpromi.room_vox.meetingscreen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform