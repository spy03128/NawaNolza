package com.example.nawanolza.hideandseek

data class PubEventRequest(
    val catchMemberId: String,
    val entryCode: String,
    val eventType: String,
    val senderId: String,
    val type: String
)