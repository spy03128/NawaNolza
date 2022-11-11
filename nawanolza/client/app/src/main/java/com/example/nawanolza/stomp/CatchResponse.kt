package com.example.nawanolza.stomp

data class CatchResponse(
    val catchMemberId: Int,
    val eventType: String,
    val gameRoomId: String,
    val senderId: Int,
    val type: String
)