package com.example.nawanolza.createGame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.databinding.ActivityGameIntroBinding
import com.example.nawanolza.databinding.ActivitySelectGameBinding
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.enterroom.EnterRoomRequest
import com.example.nawanolza.retrofit.enterroom.EnterRoomResponse
import com.example.nawanolza.retrofit.enterroom.EnterRoomService
import com.google.gson.GsonBuilder
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
            val intent = Intent(this, SettingHideSeek::class.java)
            startActivity(intent)
        }

        val retrofitAPI = RetrofitConnection.getInstance().create(
            EnterRoomService::class.java
        )

        binding.btnEnterRoom.setOnClickListener {
            val memberId = LoginUtil.getMember(this@GameIntro)!!.id
            enterRoomRequest = EnterRoomRequest(memberId, binding.EditText.text.toString())

            retrofitAPI.postEnterRoom(enterRoomRequest).enqueue(object:
                Callback<EnterRoomResponse> {
                override fun onResponse(
                    call: Call<EnterRoomResponse>,
                    response: Response<EnterRoomResponse>
                ) {
                    val intent = Intent(this@GameIntro, Waiting::class.java)
                    intent.putExtra("entryCode", response.body()?.entryCode)
                    when(response.code()){
                        200 -> {
                            intent.putExtra("data", GsonBuilder().create().toJson(response.body()))
                            startActivity(intent)
                        }
                        400 -> Toast.makeText(this@GameIntro, "이미 진행 중인 게임입니다.", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@GameIntro, "게임 정보를 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<EnterRoomResponse>, t: Throwable) {
                    println(call)
                    println(t)
                }

            })
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

}