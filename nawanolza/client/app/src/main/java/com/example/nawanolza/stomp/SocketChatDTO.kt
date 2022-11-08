package com.example.nawanolza.stomp

import com.example.nawanolza.retrofit.Member

class SocketChatDTO(member : Member, entryCode : String, message: String) {
    val entryCode: String
    val senderId: Number
    val type: SocketType
    val senderName: String
    val senderImage: String
    val message: String

    init {
        this.senderId = member.id
        this.senderName = member.name
        this.senderImage = member.image
        this.entryCode = entryCode
        this.message = message
        this.type = SocketType.CHAT
    }
}