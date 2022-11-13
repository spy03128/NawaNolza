package com.example.nawanolza.hideandseek

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nawanolza.databinding.FinishRvItemBinding
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient

class FinishRvAdapter (private val context: Context)
    :RecyclerView.Adapter<FinishRvAdapter.MyViewHolder>(){
    inner class MyViewHolder(binding: FinishRvItemBinding):
        RecyclerView.ViewHolder(binding.root){
        val username = binding.username
        val profileImg = binding.profileImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: FinishRvItemBinding = FinishRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = WaitingStompClient.winnerList[position]


        holder.apply{
            Glide.with(context).load(data.image).circleCrop().into(profileImg)
            username.text = data.name
        }
    }

    override fun getItemCount(): Int {
        return WaitingStompClient.winnerList.size
    }
}
