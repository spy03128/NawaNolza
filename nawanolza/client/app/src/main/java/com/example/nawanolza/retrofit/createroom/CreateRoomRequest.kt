package com.example.nawanolza.retrofit.createroom

data class CreateRoomRequest(
    val lat: Double?,
    val lng: Double?,
    val playGame: Int,
    val hideGame: Int,
    val range: Int,
    val hostId: Int = 4,
)
