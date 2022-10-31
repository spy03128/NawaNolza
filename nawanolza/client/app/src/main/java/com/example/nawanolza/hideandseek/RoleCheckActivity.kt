package com.example.nawanolza.hideandseek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.R
import com.example.nawanolza.databinding.ActivityRoleCheckAcitivityBinding

class RoleCheckActivity : AppCompatActivity() {

    lateinit var binding: ActivityRoleCheckAcitivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleCheckAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 술래 참여자 나눠서 fragment 보여주기
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentFrame, ParticipantsFragment())
        fragmentTransaction.commit()
    }

}