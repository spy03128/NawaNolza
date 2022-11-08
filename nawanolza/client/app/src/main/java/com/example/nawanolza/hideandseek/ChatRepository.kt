package com.example.nawanolza.hideandseek

import com.example.nawanolza.stomp.SocketChatDTO

class ChatRepository {
    companion object {
        val chatData : ArrayList<SocketChatDTO> = ArrayList()

        fun addChat(data : SocketChatDTO) {
            chatData.add(data)
        }

        fun clearData() {
            chatData.clear()
        }
    }
}