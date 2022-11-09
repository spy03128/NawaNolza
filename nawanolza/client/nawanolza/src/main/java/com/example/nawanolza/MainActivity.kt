package com.example.nawanolza

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.nawanolza.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        Toast.makeText(this,"김땡떙님이 잡혔습니다!", Toast.LENGTH_SHORT).show();

    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra(MessageConstants.message)
            val path = intent?.getStringExtra(MessageConstants.path)
            Log.i("Main Activity", "Broadcast Received. message: $message, path: $path")
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(MessageConstants.intentName)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(messageReceiver, intentFilter)

    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(messageReceiver)
    }
}