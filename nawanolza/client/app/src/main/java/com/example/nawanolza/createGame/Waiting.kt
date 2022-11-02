package com.example.nawanolza.createGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nawanolza.R
import com.example.nawanolza.databinding.ActivityWaitingBinding
import com.example.nawanolza.hideandseek.RoleCheckActivity


class Waiting : AppCompatActivity() {
    lateinit var binding: ActivityWaitingBinding
    lateinit var adapter: WaitingRvAdapter

    private var waitingMember = arrayListOf(
        WaitingMember(1,"노현우","1"),
        WaitingMember(2,"김땡땡","2"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, RoleCheckActivity::class.java )
            startActivity(intent)
        }
        adapter =WaitingRvAdapter(waitingMember)
        binding.mRecyclerView.adapter = adapter
        binding.mRecyclerView.layoutManager =GridLayoutManager(this, 3)
    }
}