package com.example.nawanolza.stomp.waitingstomp

import android.annotation.SuppressLint
import android.util.Log
import com.example.nawanolza.stomp.SocketCommonDto
import com.example.nawanolza.stomp.SocketType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class WaitingStompClient {
    companion object {
        val url = "wss://k7d103.p.ssafy.io/api/ws"
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        val objectMapper = ObjectMapper()

        lateinit var memberList: ArrayList<waitingMemberData>

//        fun runStomp(roomNumber: String) {
//            stompClient.connect()
//            stompClient.topic("/sub/participate/${roomNumber}").subscribe{ topicMessage ->
//                Log.i("message receive", topicMessage.payload)
//            }
//
//
//        }

        // 소켓 연결
        fun connect() {
            stompClient.connect()

            stompClient.lifecycle().subscribe { lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.i("OPEND", "!!")
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        Log.i("CLOSED", "!!")
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.i("ERROR", "!!")
                        Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())

                    }
                    else ->{
                        Log.i("ELSE", lifecycleEvent.message)
                    }
                }
            }
        }


        // 연결 해제
        fun disconnect() {
            stompClient.disconnect()
        }

        // 구독 후 메세지 받음
        fun receive(type: SocketType, roomNumber: String){
            stompClient.topic("/sub/" + type.value + "/" + roomNumber).subscribe { topicMessage ->
                Log.i("message Receive", topicMessage.payload)
//                Gson().fromJson(topicMessage.payload, memberList::class.java)
            }
        }

        // 데이터 전송
        fun send(type: SocketType, dto: SocketCommonDto){
            val data = objectMapper.writeValueAsString(dto)
            stompClient.send("/pub/"+type.value, data).subscribe()
        }
    }

}


