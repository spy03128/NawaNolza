package com.example.nawanolza.hideandseek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nawanolza.R
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.createGame.WaitingMember
import com.example.nawanolza.databinding.MemberDetailRvItemBinding

class MemberRvAdapter (private val context: Context)
:RecyclerView.Adapter<MemberRvAdapter.MyViewHolder>(){
    inner class MyViewHolder(binding: MemberDetailRvItemBinding):
            RecyclerView.ViewHolder(binding.root){
                val profileImg = binding.profileImg
                val username = binding.username
                val location = binding.progressBar
                val status = binding.catchStatus
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: MemberDetailRvItemBinding = MemberDetailRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val runnerNum = Waiting.runnerList[position]
        val userInfo = Waiting.memberHash[runnerNum]
        holder.apply{
            Glide.with(context).load(userInfo?.image).circleCrop().into(profileImg)
            username.text = userInfo?.name
            val userStatus = if(userInfo?.location!!) R.drawable.user_status_false else R.drawable.user_status_true
            location.setProgressDrawableTiled(AppCompatResources.getDrawable(context, userStatus))
            if(!userInfo.status){
                status.visibility = View.INVISIBLE
            } else status.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return Waiting.runnerList.size
    }


}
