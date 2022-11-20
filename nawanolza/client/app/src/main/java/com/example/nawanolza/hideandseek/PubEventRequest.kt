package com.example.nawanolza.hideandseek

data class PubEventRequest(
    val catchMemberId: Int,
    val entryCode: String,
    val eventType: String,
    val senderId: Int,
    val type: String
)