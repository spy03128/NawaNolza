package com.example.nawanolza.hideandseek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.createGame.WaitingMember
import com.example.nawanolza.databinding.ActivityMemberDetailBinding

class MemberDetail : AppCompatActivity() {
    lateinit var binding: ActivityMemberDetailBinding
    lateinit var adapter: MemberRvAdapter

    private var waitingMember = arrayListOf(
        WaitingMember(1,"노현우","1"),
        WaitingMember(2,"김땡땡","2"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3"),
        WaitingMember(3,"노땡땡","3"),
    )

    private var itMember = arrayListOf(
        WaitingMember(1,"노현우","1"),
        WaitingMember(2,"김땡땡","2"),
        WaitingMember(3,"노땡땡","3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ppUpdate()
        itUpdate()
    }

    private fun itUpdate(){
        // 원래 멤버는 it멤버 넣어야해
        adapter = MemberRvAdapter(itMember)
        binding.itRv.adapter = adapter
        binding.itRv.layoutManager = GridLayoutManager(this, 4)
    }

    private fun ppUpdate(){
        adapter = MemberRvAdapter(waitingMember)
        binding.ppRv.adapter = adapter
        binding.ppRv.layoutManager = GridLayoutManager(this, 4)
    }
}