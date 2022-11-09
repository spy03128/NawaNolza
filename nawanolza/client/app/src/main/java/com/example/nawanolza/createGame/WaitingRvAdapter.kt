package com.example.nawanolza.createGame

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nawanolza.databinding.WaitingRvItemBinding

class WaitingRvAdapter (context: Context)
:RecyclerView.Adapter<WaitingRvAdapter.MyViewHolder>(){
    val context: Context

    inner class MyViewHolder(binding: WaitingRvItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val profileImg = binding.profileImg
            val username = binding.username
        }

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: WaitingRvItemBinding = WaitingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val waitingData = Waiting.memberList[position]
//        holder.username.text = waitingData.name

        holder.apply{
            Glide.with(context).load(waitingData.image).circleCrop().into(profileImg)
            username.text = waitingData.name
        }

    }

    override fun getItemCount(): Int {
        return Waiting.memberList.size
    }
}