package com.example.nawanolza.stomp.waitingstomp

import android.annotation.SuppressLint
import android.util.Log
import com.example.nawanolza.stomp.SocketCommonDto
import com.example.nawanolza.stomp.SocketType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class WaitingStompClient {
    companion object {
        private val url = "wss://k7d103.p.ssafy.io/api/ws"
        private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        private val objectMapper = ObjectMapper()
        lateinit var data: ArrayList<waitingMemberData>

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

        // 구독
        fun subscribe(type: SocketType, roomNumber: Number){
            stompClient.topic("/sub/" + type.value + "/" + roomNumber).subscribe { topicMessage ->
                Log.i("message Recieve", topicMessage.payload)
            }
        }

        // 데이터 전송
        fun send(type: SocketType, dto: SocketCommonDto){
            val data = objectMapper.writeValueAsString(dto)
            stompClient.send("/pub"+type.value, data).subscribe()
        }

    }

}


