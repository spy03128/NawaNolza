package com.example.nawanolza.hideandseek

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.nawanolza.databinding.ChattingRvItemLeftBinding
import com.example.nawanolza.databinding.ChattingRvItemRightBinding


class ChattingRvAdapter(
    private val chatData: ArrayList<ChatDTO>,
    private val application: Application,
) :RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == ChatType.LEFT) {
            val binding = ChattingRvItemLeftBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LeftViewHolder(binding)
        } else {
            val binding = ChattingRvItemRightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RightViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return chatData.size
    }

    inner class LeftViewHolder(itemView: ChattingRvItemLeftBinding) : ViewHolder(itemView.root) {
        var content: TextView
        var name: TextView
        var image: ImageView

        init {
            content = itemView.content
            name = itemView.name
            image = itemView.profileImg
        }
    }

    inner class RightViewHolder(itemView: ChattingRvItemRightBinding) : ViewHolder(itemView.root) {
        var content: TextView

        init {
            content = itemView.content
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val chatInfo = chatData.get(position)
        if (viewHolder is LeftViewHolder) {
            viewHolder.name.setText(chatInfo.senderName)
            viewHolder.content.setText(chatInfo.message)
            Glide.with(application).load(chatInfo.senderImage).into(viewHolder.image)
        } else {
            (viewHolder as RightViewHolder).content.setText(chatInfo.message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return chatData.get(position).viewType
    }
}


