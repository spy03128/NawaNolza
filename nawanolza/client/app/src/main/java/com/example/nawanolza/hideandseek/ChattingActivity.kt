package com.example.nawanolza.hideandseek

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.CharacterRvAdapter
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.databinding.ActivityChattingBinding
import com.example.nawanolza.retrofit.Member
import com.example.nawanolza.retrofit.createroom.MemberList
import com.example.nawanolza.stomp.SocketChatDTO
import com.example.nawanolza.stomp.SocketType
import com.example.nawanolza.stomp.StompClient
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_chatting.*

class ChattingActivity : AppCompatActivity() {
    lateinit var binding: ActivityChattingBinding
    lateinit var chatData: ArrayList<ChatDTO>
    lateinit var adapter: ChattingRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WaitingStompClient.connect()

//        val entryCode = intent.getStringExtra("entryCode")
//        val member = LoginUtil.getMember(this)!!
        val entryCode = "1234"
        val member = Member("", "", 10, "http://k.kakaocdn.net/dn/rekq2/btrQrFFtJMK/E3rOBQjbKuKRKzMNQ2KWu1/img_640x640.jpg", "권도현")
        chatData = ChattingUtil.getChatData(entryCode!!)
        chatData.add(ChatDTO(SocketChatDTO(member, "1234","안녕ㅎ"), ChatType.LEFT))
        chatData.add(ChatDTO(SocketChatDTO(member, "1234","안녕123"), ChatType.RIGHT))
        chatData.add(ChatDTO(SocketChatDTO(member, "1234","안녕435"), ChatType.LEFT))

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        messageSubscribe(entryCode!!, member.id)

        sendButton.setOnClickListener {
            val socketChatDTO = SocketChatDTO(member!!, entryCode, messageInput.text.toString())
            sendMessage(socketChatDTO)
            binding.messageInput.setText("")
        }

        println("=========chatting oncreate")
        println(chatData.size)
        println(chatData)


        adapter = ChattingRvAdapter(chatData, application)
        binding.chattingRecyclerView.adapter = adapter
        binding.chattingRecyclerView.layoutManager =
            GridLayoutManager(this@ChattingActivity, 1)




    }

    private fun messageSubscribe(
        entryCode: String,
        memberId: Int,
    ) {
        WaitingStompClient.stompClient.topic("/sub/chat/" + entryCode).subscribe { topicMessage ->
            Log.i("message Receive", topicMessage.payload)
            val fromJson = GsonBuilder().create().fromJson(topicMessage.payload, SocketChatDTO::class.java)

            if(!fromJson.senderId.equals(memberId))
                chatData.add(ChatDTO(fromJson, ChatType.LEFT))

            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
            println("=========chatting")
            println(chatData.size)
            println(chatData)
        }
        println("/sub/" + SocketType.CHAT.value + "/" + entryCode)
    }

    private fun sendMessage(dto: SocketChatDTO) {
        val data = GsonBuilder().create().toJson(dto)
        println(dto.type)
        println(dto.senderId)
        println(dto.senderName)
        println(dto.senderImage)
        println(dto.message)
        chatData.add(ChatDTO(dto, ChatType.RIGHT))
        WaitingStompClient.stompClient.send("/pub/"+ SocketType.CHAT.value, data).subscribe()
    }
}