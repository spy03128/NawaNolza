package com.example.nawanolza.createGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawanolza.databinding.WaitingRvItemBinding

class WaitingRvAdapter (private val waitingMember: ArrayList<WaitingMember>)
:RecyclerView.Adapter<WaitingRvAdapter.MyViewHolder>(){
    inner class MyViewHolder(binding: WaitingRvItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val profileImg = binding.profileImg
            val username = binding.username
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: WaitingRvItemBinding = WaitingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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