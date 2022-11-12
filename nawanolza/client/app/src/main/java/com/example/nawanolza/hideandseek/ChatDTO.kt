package com.example.nawanolza.hideandseek

import com.example.nawanolza.retrofit.Member
import com.example.nawanolza.stomp.SocketChatDTO
import com.example.nawanolza.stomp.SocketType

class ChatDTO(dto: SocketChatDTO, viewType: Int) {
    val entryCode: String
    val senderId: Number
    val senderName: String
    val senderImage: String
    val message: String
    val viewType: Int

    init {
        this.senderId = dto.senderId
        this.senderName = dto.senderName
        this.senderImage = dto.senderImage
        this.entryCode = dto.entryCode
        this.message = dto.message
        this.viewType = viewType
    }
}