package com.example.nawanolza.hideandseek

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawanolza.createGame.WaitingMember
import com.example.nawanolza.databinding.HideseekmemberRvItemBinding

class HideSeekRvAdapter (private val waitingMember: ArrayList<WaitingMember>)
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
        val waitingData = waitingMember[position]
        holder.username.text = waitingData.name
    }

    override fun getItemCount(): Int {
        return waitingMember.size
    }
    }
