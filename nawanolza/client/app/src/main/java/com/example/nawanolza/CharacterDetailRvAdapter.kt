package com.example.nawanolza

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nawanolza.databinding.CharacterDetailRvItemBinding
import com.example.nawanolza.databinding.CharacterRvItemBinding
import com.example.nawanolza.retrofit.CharacterDetailResponse
import com.example.nawanolza.retrofit.CollectionItem
import com.example.nawanolza.retrofit.History

class CharacterDetailRvAdapter(
    private val characterInfo: List<History>,
    private val application: Application,
)
:RecyclerView.Adapter<CharacterDetailRvAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding: CharacterDetailRvItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val level = binding.collectionLevel
            val date = binding.collectionDate
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CharacterDetailRvItemBinding = CharacterDetailRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        println(characterInfo.size)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val collectionInfo = characterInfo[position]
        println(collectionInfo.level.toString())
        println(collectionInfo.createdAt)

        holder.level.text = "Lv."+ collectionInfo.level.toString()
        holder.date.text = collectionInfo.createdAt

    }

    override fun getItemCount(): Int {
        return characterInfo.size
    }


}