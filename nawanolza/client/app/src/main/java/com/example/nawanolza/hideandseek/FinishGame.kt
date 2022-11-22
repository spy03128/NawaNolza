package com.example.nawanolza.hideandseek

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.HomeActivity
import com.example.nawanolza.databinding.ActivityFinishGameBinding
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import kotlinx.android.synthetic.main.activity_finish_game.*

class FinishGame : AppCompatActivity() {
    lateinit var binding: ActivityFinishGameBinding
    lateinit var adapter: FinishRvAdapter

    companion object {
        var _Finish_Game: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _Finish_Game = this@FinishGame

        binding = ActivityFinishGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = if(WaitingStompClient.winTagger) "술래팀 승리!" else "숨는팀 승리!"

        ExitBtn.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        adapter = FinishRvAdapter(this)
        binding.mRecyclerView.adapter = adapter

        if(WaitingStompClient.winTagger) {
            binding.mRecyclerView.layoutManager = GridLayoutManager(this, 1)
        } else{
            binding.mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        }
    }

    override fun onPause() {
        super.onPause()
        println("================Pause LAST===============")
    }

    override fun onDestroy() {
        super.onDestroy()

        MainHideSeek._MainHiddSeek_Activity?.finish()
    }
}