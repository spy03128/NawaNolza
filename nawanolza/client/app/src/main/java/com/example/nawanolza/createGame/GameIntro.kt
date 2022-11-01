package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.databinding.ActivityGameIntroBinding
import com.example.nawanolza.databinding.ActivitySelectGameBinding

class GameIntro : AppCompatActivity() {
    lateinit var binding: ActivityGameIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCreateRoom.setOnClickListener {
            val intent = Intent(this, SelectGame::class.java)
            startActivity(intent)
        }

        binding.btnEnterRoom.setOnClickListener {
            val intent = Intent(this, Waiting::class.java)
            startActivity(intent)
        }

    }
}