package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.nawanolza.databinding.ActivityGameIntroBinding
import com.example.nawanolza.databinding.ActivitySelectGameBinding
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.enterroom.EnterRoomRequest
import com.example.nawanolza.retrofit.enterroom.EnterRoomResponse
import com.example.nawanolza.retrofit.enterroom.EnterRoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameIntro : AppCompatActivity() {
    lateinit var binding: ActivityGameIntroBinding
    private lateinit var enterRoomRequest: EnterRoomRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCreateRoom.setOnClickListener {
            val intent = Intent(this, SelectGame::class.java)
            startActivity(intent)
        }

        val retrofitAPI = RetrofitConnection.getInstance().create(
            EnterRoomService::class.java
        )

        binding.btnEnterRoom.setOnClickListener {
            enterRoomRequest = EnterRoomRequest(3, binding.EditText.text.toString())

            retrofitAPI.postEnterRoom(enterRoomRequest).enqueue(object:
                Callback<EnterRoomResponse> {
                override fun onResponse(
                    call: Call<EnterRoomResponse>,
                    response: Response<EnterRoomResponse>
                ) {
                    val intent = Intent(this@GameIntro, Waiting::class.java)
                    intent.putExtra("code", response.body()?.entryCode)
                    when(response.code()){
                        200 -> startActivity(intent)
                        else -> Toast.makeText(this@GameIntro, "잘못된 정보입니다", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<EnterRoomResponse>, t: Throwable) {
                    println(call)
                    println(t)
                }
            })

        }
    }
}