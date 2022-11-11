package com.example.nawanolza.hideandseek

class ChattingUtil {
    companion object {
        val chatMap: HashMap<String, ArrayList<ChatDto>> = HashMap()

        fun existChatData(entryCode: String) : Boolean {
            return chatMap.containsKey(entryCode)
        }

        fun getChatData(entryCode: String): ArrayList<ChatDto> {
            return chatMap.get(entryCode)!!
        }

        fun clearChatData(entryCode: String) {
            chatMap.get(entryCode)!!.clear()
        }
    }
}