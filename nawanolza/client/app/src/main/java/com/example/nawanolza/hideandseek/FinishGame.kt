package com.example.nawanolza.hideandseek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.HomeActivity
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.databinding.ActivityFinishGameBinding
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import kotlinx.android.synthetic.main.activity_finish_game.*

class FinishGame : AppCompatActivity() {
    lateinit var binding: ActivityFinishGameBinding
    lateinit var adapter: FinishRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = if(WaitingStompClient.winTagger) "술래팀 승리!" else "숨는팀 승리!"

        ExitBtn.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        adapter = FinishRvAdapter(this)
        binding.mRecyclerView.adapter = adapter
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 4)
    }

    override fun onPause() {
        super.onPause()
        println("================Pause LAST===============")
    }
}