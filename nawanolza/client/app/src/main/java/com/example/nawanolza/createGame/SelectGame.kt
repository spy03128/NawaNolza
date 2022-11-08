package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.databinding.ActivitySelectGameBinding
import com.example.nawanolza.hideandseek.TokenActivity
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.createroom.CreateRoomHideResponse
import com.example.nawanolza.retrofit.createroom.CreateRoomHideService
import kotlinx.android.synthetic.main.activity_select_game.*
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

        binding.goToken.setOnClickListener {
            val intent = Intent(this, TokenActivity::class.java )
            startActivity(intent)
        }
    }

}
