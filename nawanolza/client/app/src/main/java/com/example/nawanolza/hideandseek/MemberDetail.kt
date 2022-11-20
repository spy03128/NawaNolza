package com.example.nawanolza.hideandseek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.nawanolza.R
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.databinding.ActivityMemberDetailBinding

class MemberDetail : AppCompatActivity() {
    lateinit var binding: ActivityMemberDetailBinding
    lateinit var adapter: MemberRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taggerUpdate()
        runnerUpdate()
    }

    private fun taggerUpdate(){
        val tagger = Waiting.memberHash[Waiting.tagger]
        binding.username.text = tagger?.name
        Glide.with(this).load(tagger?.image).circleCrop().into(binding.profileImg)
        val userLocation = if(tagger?.location!!) R.drawable.user_status_false else R.drawable.user_status_true
        binding.progressBar.setProgressDrawableTiled(AppCompatResources.getDrawable(this, userLocation))
    }

    private fun runnerUpdate(){
        adapter = MemberRvAdapter(this)
        binding.ppRv.adapter = adapter
        binding.ppRv.layoutManager = GridLayoutManager(this, 4)
    }
}