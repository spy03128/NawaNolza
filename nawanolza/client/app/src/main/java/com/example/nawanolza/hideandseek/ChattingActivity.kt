package com.example.nawanolza.hideandseek

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.databinding.ActivityChattingBinding
import com.example.nawanolza.stomp.SocketChatDTO
import com.example.nawanolza.stomp.SocketType
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_chatting.*
import java.time.LocalDateTime
import kotlin.properties.Delegates

class ChattingActivity : AppCompatActivity() {
    lateinit var binding: ActivityChattingBinding
    lateinit var chatData: ArrayList<ChatDTO>
    lateinit var adapter: ChattingRvAdapter
    var flag by Delegates.notNull<Boolean>()

    companion object {
        var _Chatting_Activity: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _Chatting_Activity = this@ChattingActivity

        WaitingStompClient.connect()
        val entryCode = intent.getStringExtra("entryCode")
        flag = intent.getBooleanExtra("flag", false)
        val member = LoginUtil.getMember(this)!!
        chatData = ChattingUtil.getChatData(entryCode!!)

        if (flag) {
            val intent = Intent(this@ChattingActivity, MainHideSeek::class.java)
            intent.putExtra("entryCode", entryCode)
            startActivity(intent)
        }

        messageSubscribe(entryCode!!, member.id)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendButton.setOnClickListener {
            val socketChatDTO = SocketChatDTO(member!!, entryCode, messageInput.text.toString(), LocalDateTime.now())
            sendMessage(socketChatDTO)
            binding.messageInput.setText("")
        }

        beforeButton.setOnClickListener{
            finish()
        }

        adapter = ChattingRvAdapter(chatData, application)
        binding.chattingRecyclerView.adapter = adapter
        binding.chattingRecyclerView.layoutManager = GridLayoutManager(this@ChattingActivity, 1)
    }

    private fun messageSubscribe(
        entryCode: String,
        memberId: Int,
    ) {
        WaitingStompClient.stompClient.topic("/sub/chat/" + entryCode).subscribe { topicMessage ->
            val fromJson = GsonBuilder().create().fromJson(topicMessage.payload, SocketChatDTO::class.java)

            if(flag && fromJson.senderId.toString() != memberId.toString())
                chatData.add(ChatDTO(fromJson, ChatType.LEFT))

            println("=======Sub=======")
            println(chatData)

            if (!flag) {
                runOnUiThread {
                    binding.chattingRecyclerView.scrollToPosition(chatData.size-1)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun sendMessage(dto: SocketChatDTO) {
        val data = GsonBuilder().create().toJson(dto)
        chatData.add(ChatDTO(dto, ChatType.RIGHT))
        WaitingStompClient.stompClient.send("/pub/"+ SocketType.CHAT.value, data).subscribe()
    }
}