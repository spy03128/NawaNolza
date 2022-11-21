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
import com.example.nawanolza.databinding.HideseekmemberRvItemBinding

class HideSeekRvAdapter (private val context: Context)
    :RecyclerView.Adapter<HideSeekRvAdapter.MyViewHolder>(){
        inner class MyViewHolder(binding: HideseekmemberRvItemBinding):
            RecyclerView.ViewHolder(binding.root){
                val username = binding.username
                val location = binding.progressBar
                val status = binding.catchStatus
                val profileImg = binding.profileImg
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: HideseekmemberRvItemBinding = HideseekmemberRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val runnerNum = Waiting.runnerList[position]
        val userInfo = Waiting.memberHash[runnerNum]

        println(userInfo)


        holder.apply{
            itemView.setOnClickListener {
                itemClickListener.onClick(it, position)
            }
            Glide.with(context).load(userInfo?.image).circleCrop().into(profileImg)
            username.text = userInfo?.name
            val userStatus = if(userInfo?.location!!) R.drawable.user_status_false else R.drawable.user_status_true
            location.setProgressDrawableTiled(AppCompatResources.getDrawable(context, userStatus))
            if(!userInfo.status){
                status.visibility = View.INVISIBLE
            } else status.visibility = View.VISIBLE
        }
    }

    // (1) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    // (2) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    // (3) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener


    override fun getItemCount(): Int {
        return Waiting.runnerList.size
    }
}
