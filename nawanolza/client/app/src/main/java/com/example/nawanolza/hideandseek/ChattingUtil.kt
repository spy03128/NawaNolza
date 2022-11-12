package com.example.nawanolza.hideandseek

import com.example.nawanolza.stomp.SocketChatDTO

class ChattingUtil {
    companion object {
        val chatMap: HashMap<String, ArrayList<SocketChatDTO>> = HashMap()

        fun existChatData(entryCode: String) : Boolean {
            return chatMap.containsKey(entryCode)
        }

        fun getChatData(entryCode: String): ArrayList<SocketChatDTO> {
            if (!existChatData(entryCode))
                chatMap.put(entryCode, ArrayList())
            return chatMap.get(entryCode)!!
        }

        fun clearChatData(entryCode: String) {
            chatMap.get(entryCode)!!.clear()
            chatMap.remove(entryCode)
        }
    }
}