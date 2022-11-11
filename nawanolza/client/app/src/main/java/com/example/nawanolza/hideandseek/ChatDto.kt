package com.example.nawanolza.hideandseek

data class ChatDto(
    val senderId: Long,
    val senderImage: String,
    val senderName: String,
    val entryCode: String,
    val message: String,
)
