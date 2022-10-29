package com.example.nawanolza.stomp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.nawanolza.R
import com.example.sockettest.SocketGpsDTO
import com.example.sockettest.SocketType
import com.example.sockettest.StompClient
/*
class ExapleStompCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var stompClient: StompClient? = null

        val socketButton = findViewById<Button>(R.id.socket_btn)
        val connectButton = findViewById<Button>(R.id.connect_btn)
        val subButton = findViewById<Button>(R.id.sub_btn)
        val sendButton = findViewById<Button>(R.id.send_btn)

        socketButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                println("소켓객체 생성 버튼클릭")
                stompClient = StompClient()
            }
        })

        connectButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                println("소켓연결 버튼클릭")
                stompClient?.connect()
            }
        })

        subButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                println("소켓구독 버튼클릭")
                stompClient?.subcribe(SocketType.GPS, 1)
            }
        })

        sendButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                println("소켓 메시지 전송 버튼클릭")
                stompClient?.send(SocketType.GPS, SocketGpsDTO(1, 2, 36.1234, 36.1234))
            }
        })
    }
}
*/