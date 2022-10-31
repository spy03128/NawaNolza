package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.R
import com.example.nawanolza.databinding.ActivityWaitingBinding
import com.example.nawanolza.hideandseek.RoleCheckActivity


class Waiting : AppCompatActivity() {
    lateinit var binding: ActivityWaitingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, RoleCheckActivity::class.java )
            startActivity(intent)
        }
    }
}