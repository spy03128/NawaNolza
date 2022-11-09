package com.example.nawanolza.retrofit.createroom

data class CreateRoomHideResponse(
    val entryCode: String,
    val hideAndSeekProperties: HideAndSeekProperties,
    val host: Host,
    val message: String,
    val participants: List<Any>
)