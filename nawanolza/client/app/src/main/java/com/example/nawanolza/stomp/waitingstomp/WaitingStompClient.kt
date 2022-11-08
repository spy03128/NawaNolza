package com.example.nawanolza.stomp.waitingstomp

import android.util.Log
import com.example.nawanolza.stomp.SocketCommonDto
import com.example.nawanolza.stomp.SocketType
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.GsonBuilder
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import kotlin.concurrent.timer

class WaitingStompClient {
    companion object {
        val url = "wss://k7d103.p.ssafy.io/api/ws"
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        val objectMapper = ObjectMapper()
        var stopFlag = true

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
            stopFlag = false

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
            timer(initialDelay = 0L, period = 1000 * 50L) {
                Log.i("타이머 실행", "타이머 실행")
                stompClient.send("", "").subscribe()
                if (stopFlag)   cancel()
            }
        }


        // 연결 해제
        fun disconnect() {
            stompClient.disconnect()
            stopFlag = true
        }

        // 구독 후 메세지 받음
        fun receive(type: SocketType, entryCode: String){
            stompClient.topic("/sub/" + type.value + "/" + entryCode).subscribe { topicMessage ->
                Log.i("message Receive", topicMessage.payload)
//                Gson().fromJson(topicMessage.payload, memberList::class.java)
            }
        }

        // 데이터 전송
        fun send(type: SocketType, dto: SocketCommonDto){
            val data = GsonBuilder().create().toJson(dto)
            stompClient.send("/pub/"+type.value, data).subscribe()
        }
    }

}


