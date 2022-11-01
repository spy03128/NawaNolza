package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.databinding.ActivitySelectGameBinding
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.createroom.CreateRoomHideResponse
import com.example.nawanolza.retrofit.createroom.CreateRoomHideService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectGame : AppCompatActivity() {

    lateinit var binding: ActivitySelectGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHideSeek.setOnClickListener {
            val intent = Intent(this, SettingHideSeek::class.java)
            startActivity(intent)
        }
    }

    private fun createRoomHide(){
        val retrofitAPI = RetrofitConnection.getInstance().create(CreateRoomHideService::class.java)

        retrofitAPI.postCreateRoomHide(mapOf("count" to 4)).enqueue(object : Callback<CreateRoomHideResponse>{
            override fun onResponse(
                call: Call<CreateRoomHideResponse>,
                response: Response<CreateRoomHideResponse>
            ) {
                response.body()?.let{
                    updateUI(it)
                }
            }

            override fun onFailure(call: Call<CreateRoomHideResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun updateUI(res: CreateRoomHideResponse){
        val code = res.roomCode
    }
}
