package com.example.nawanolza.hideandseek

import android.app.Application
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nawanolza.databinding.ChattingRvItemBinding
import com.example.nawanolza.stomp.SocketChatDTO
import kotlinx.android.synthetic.main.chatting_rv_item.view.*


class ChattingRvAdapter(
    private val chatData: ArrayList<SocketChatDTO>,
    private val application: Application,
)
:RecyclerView.Adapter<ChattingRvAdapter.MyViewHolder>(){

    var message_left = false

    inner class MyViewHolder(binding: ChattingRvItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val name = binding.name
            val content = binding.content
            val profileImage = binding.profileImg
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ChattingRvItemBinding = ChattingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chatInfo = chatData[position]

        println("===chat size===")
        println(chatInfo.senderName)

        holder.name.text = chatInfo.senderName
        //holder.content.background = application.getDrawable((if(!message_left)  R.drawable.chatting_left else R.drawable.chatting_right))
        holder.content.text = chatInfo.message
        Glide.with(application).load(chatInfo.senderImage).into(holder.profileImage)


        val align: Int

        if (!message_left) {
            align = Gravity.LEFT
            message_left = false
        } else {
            align = Gravity.RIGHT
            message_left = true
        }

//        holder.container.gravity = align

    }

    override fun getItemCount(): Int {
        return chatData.size
    }
}


