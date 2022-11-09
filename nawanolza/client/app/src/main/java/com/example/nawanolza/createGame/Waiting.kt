package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.nawanolza.databinding.ActivityWaitingBinding
import com.example.nawanolza.hideandseek.RoleCheckActivity
import com.example.nawanolza.retrofit.createroom.MemberList
import com.example.nawanolza.retrofit.enterroom.EnterRoomResponse
import com.example.nawanolza.stomp.SocketType
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import com.google.gson.GsonBuilder
import io.reactivex.Emitter
import kotlinx.android.synthetic.main.activity_waiting.*
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import kotlin.concurrent.timer
import kotlin.properties.Delegates

class Waiting : AppCompatActivity() {
    lateinit var binding: ActivityWaitingBinding
    lateinit var roomInfo: EnterRoomResponse
    lateinit var adapter: WaitingRvAdapter

    companion object {
        var memberList: ArrayList<WaitingMember> = ArrayList()
    }

//    private var waitingMember = arrayListOf(
//        WaitingMember(1,"노현우","1"),
//        WaitingMember(2,"김땡땡","2"),
//        WaitingMember(3,"노땡땡","3"),
//        WaitingMember(3,"노땡땡","3"),
//        WaitingMember(3,"노땡땡","3"),
//        WaitingMember(3,"노땡땡","3"),
//        WaitingMember(3,"노땡땡","3"),
//        WaitingMember(3,"노땡땡","3"),
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entryCode = intent.getStringExtra("entryCode")

        roomInfo = GsonBuilder().create()
            .fromJson(intent.getStringExtra("data"), EnterRoomResponse::class.java)
        hostName.text = roomInfo.host.name
        Glide.with(this).load(roomInfo.host.image).circleCrop().into(hostImg)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, RoleCheckActivity::class.java)
            intent.putExtra("entryCode", entryCode)

            startActivity(intent)
        }
        adapter = WaitingRvAdapter(this)
        binding.mRecyclerView.adapter = adapter
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 3)

        codeNumber.text = entryCode

        WaitingStompClient.connect()
        WaitingStompClient.receive(SocketType.PARTICIPATE, entryCode.toString())

        check.setOnClickListener {
            check()
        }
//        WaitingStompClient.send(SocketType.GPS, SocketGpsDTO(roomCode.toString(), 4, 37.5666102, 126.9783881))
    }

    fun check() {
        adapter.notifyDataSetChanged()
    }
}