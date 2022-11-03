package com.example.nawanolza.hideandseek

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawanolza.createGame.WaitingMember
import com.example.nawanolza.databinding.MemberDetailRvItemBinding

class ParticipantMemberRvAdapter (private val waitingMember: ArrayList<WaitingMember>)
    :RecyclerView.Adapter<ParticipantMemberRvAdapter.MyViewHolder>(){
    inner class MyViewHolder(binding: MemberDetailRvItemBinding):
        RecyclerView.ViewHolder(binding.root){
        val profileImg = binding.profileImg
        val username = binding.username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: MemberDetailRvItemBinding = MemberDetailRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val playerData = waitingMember[position]
        holder.username.text = playerData.name
    }

    override fun getItemCount(): Int {
        return waitingMember.size
    }


}