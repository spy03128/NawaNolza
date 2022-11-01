package com.example.nawanolza.hideandseek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.databinding.ActivityMainHideSeekBinding

class MainHideSeek : AppCompatActivity() {
    lateinit var binding: ActivityMainHideSeekBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHideSeekBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}