package com.example.nawanolza.hideandseek

import com.example.nawanolza.stomp.SocketChatDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatDTO(dto: SocketChatDTO, viewType: Int) {
    val entryCode: String
    val senderId: Number
    val senderName: String
    val senderImage: String
    val message: String
    val viewType: Int
    val chatTime : LocalDateTime

    init {
        this.senderId = dto.senderId
        this.senderName = dto.senderName
        this.senderImage = dto.senderImage
        this.entryCode = dto.entryCode
        this.message = dto.message
        this.viewType = viewType
        chatTime = LocalDateTime.parse(dto.chatTime.split(".").get(0), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
    }
}