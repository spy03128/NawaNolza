package com.example.nawanolza.hideandseek

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawanolza.databinding.CharacterRvItemBinding
import com.example.nawanolza.databinding.ChattingRvItemBinding
import com.example.nawanolza.retrofit.CollectionItem
import com.example.nawanolza.stomp.SocketChatDTO

class ChattingRvAdapter(
    private val chatData: ArrayList<SocketChatDTO>,
    private val application: Application,
)
:RecyclerView.Adapter<ChattingRvAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding: ChattingRvItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val characterImg = binding.characterImg
            val level = binding.level
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ChattingRvItemBinding = ChattingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val characterInfo = chatData[position]



//        holder.level.text = "Lv." + characterInfo.currentLevel
    }

    override fun getItemCount(): Int {
        return chatData.size
    }
}