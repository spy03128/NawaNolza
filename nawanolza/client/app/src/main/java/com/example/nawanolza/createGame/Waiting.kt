package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.databinding.ActivityWaitingBinding
import com.example.nawanolza.hideandseek.RoleCheckActivity
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.createroom.MemberList
import com.example.nawanolza.retrofit.enterroom.EnterRoomResponse
import com.example.nawanolza.retrofit.enterroom.GetRoomResponse
import com.example.nawanolza.retrofit.enterroom.GetRoomService
import com.example.nawanolza.stomp.SocketType
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import com.google.gson.GsonBuilder
import io.reactivex.Emitter
import kotlinx.android.synthetic.main.activity_waiting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import kotlin.concurrent.timer
import kotlin.properties.Delegates

class Waiting : AppCompatActivity() {
    lateinit var binding: ActivityWaitingBinding
    lateinit var roomInfo: EnterRoomResponse
    lateinit var adapter: WaitingRvAdapter

    companion object {
        var hostId = 0
        lateinit var hostInfo: WaitingMember
        var memberList: ArrayList<WaitingMember> = ArrayList()
        var memberHash: HashMap<Int, WaitingMember> = HashMap()
        var tagger: Int = 0
        var runnerList: List<Int> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entryCode = intent.getStringExtra("entryCode")

        roomInfo = GsonBuilder().create()
            .fromJson(intent.getStringExtra("data"), EnterRoomResponse::class.java)

        memberList = roomInfo.participants
        hostId = roomInfo.host.memberId

        val host = roomInfo.host
        hostInfo = WaitingMember(host.memberId, host.name, host.image)

        hostName.text = roomInfo.host.name
        Glide.with(this).load(roomInfo.host.image).circleCrop().into(hostImg)

        val retrofitAPI = RetrofitConnection.getInstance().create(
            GetRoomService::class.java
        )

        if (hostId != LoginUtil.getMember(this)?.id){
            btnStart.visibility = View.INVISIBLE
        }

        binding.btnStart.setOnClickListener {
            startGame(retrofitAPI, entryCode)
        }

        setAdapter()

        codeNumber.text = entryCode

        connecStomp(entryCode)
    }

    private fun connecStomp(entryCode: String?) {
        WaitingStompClient.connect()
        WaitingStompClient.subParticipate(entryCode.toString(), adapter, this)
        WaitingStompClient.subGameStart(entryCode.toString(), this@Waiting)
    }

    private fun setAdapter() {
        adapter = WaitingRvAdapter(this)
        binding.mRecyclerView.adapter = adapter
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun startGame(
        retrofitAPI: GetRoomService,
        entryCode: String?
    ) {
        retrofitAPI.getRoomInfo(entryCode.toString()).enqueue(object :
            Callback<GetRoomResponse> {
            override fun onResponse(
                call: Call<GetRoomResponse>,
                response: Response<GetRoomResponse>
            ) {
                when (response.code()) {
                    else -> Toast.makeText(this@Waiting, "게임 시작 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetRoomResponse>, t: Throwable) {
                println(call)
                println(t)
            }
        })
    }
}