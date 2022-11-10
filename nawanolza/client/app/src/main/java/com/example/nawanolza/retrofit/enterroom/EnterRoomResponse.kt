package com.example.nawanolza.retrofit.enterroom

import com.example.nawanolza.createGame.WaitingMember

data class EnterRoomResponse(
    val entryCode: String,
    val hideAndSeekProperties: HideAndSeekProperties,
    val host: HostId,
    val message: String,
    val participants: ArrayList<WaitingMember>
)