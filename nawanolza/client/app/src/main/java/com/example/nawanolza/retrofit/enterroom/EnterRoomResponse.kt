package com.example.nawanolza.retrofit.enterroom

data class EnterRoomResponse(
    val entryCode: String,
    val hideAndSeekProperties: HideAndSeekProperties,
    val host: HostId,
    val message: String,
    val participants: List<Participant>
)