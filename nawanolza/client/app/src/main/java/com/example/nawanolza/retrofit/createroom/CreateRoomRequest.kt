package com.example.nawanolza.retrofit.createroom

data class CreateRoomRequest(
    val lat: Double?,
    val lng: Double?,
    val playTime: Int,
    val hideTime: Int,
    val range: Int,
    val hostId: Int,
)
