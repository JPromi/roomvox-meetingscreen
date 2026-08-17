package com.jpromi.room_vox.meetingscreen.models

import kotlinx.serialization.Serializable

@Serializable
data class RoomStatus(
    var room: Room,
    var status: String,
    var currentBooking: String, // ToDo
    var nextBooking: String, // ToDo
    var freeUntil: String, // ToDo
    var todayBookings: List<String> // ToDo
)