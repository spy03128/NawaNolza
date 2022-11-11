package com.example.nawanolza.hideandseek

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nawanolza.MarkerImageUtil
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.databinding.HideseekmemberRvItemBinding

class HideSeekRvAdapter (private val context: Context)
    :RecyclerView.Adapter<HideSeekRvAdapter.MyViewHolder>(){
        inner class MyViewHolder(binding: HideseekmemberRvItemBinding):
            RecyclerView.ViewHolder(binding.root){
                val username = binding.username
                val status = binding.progressBar
                val profileImg = binding.profileImg
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: HideseekmemberRvItemBinding = HideseekmemberRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val waitingData = Waiting.memberList[position]

        holder.apply{
            Glide.with(context).load(waitingData.image).circleCrop().into(profileImg)
            username.text = waitingData.name

        }
    }

    override fun getItemCount(): Int {
        return Waiting.memberList.size
    }
}
