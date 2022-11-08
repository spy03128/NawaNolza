package com.example.nawanolza.hideandseek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.databinding.ActivityChattingBinding
import com.example.nawanolza.stomp.SocketChatDTO
import com.example.nawanolza.stomp.SocketType
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_chatting.*

class ChattingActivity : AppCompatActivity() {
    lateinit var binding: ActivityChattingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val entryCode = intent.getStringExtra("entryCode")
        val member = LoginUtil.getMember(this)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        messageSubscribe(entryCode!!)

        sendButton.setOnClickListener {
            val socketChatDTO = SocketChatDTO(member!!, entryCode, messageInput.text.toString())
            sendMessage(socketChatDTO)
            binding.messageInput.setText("")
        }
    }

    private fun messageSubscribe(
        entryCode: String,
    ) {
        WaitingStompClient.stompClient.topic("/sub/" + SocketType.CHAT.value + "/" + entryCode)
            .subscribe { topicMessage ->
                val message = Gson().fromJson(topicMessage.payload, SocketChatDTO::class.java)
                ChatRepository.addChat(message)
            }
    }

    private fun sendMessage(dto: SocketChatDTO) {
        val data = GsonBuilder().create().toJson(dto)
        WaitingStompClient.stompClient.send("/pub/"+ SocketType.CHAT.value, data).subscribe()
    }
}