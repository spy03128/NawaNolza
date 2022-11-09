package com.example.nawanolza.hideandseek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.nawanolza.R
import com.example.nawanolza.databinding.ActivityRoleCheckAcitivityBinding
import kotlinx.android.synthetic.main.activity_role_check_acitivity.*
import kotlinx.android.synthetic.main.fragment_participants.*

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

        object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var time = (millisUntilFinished / 1000).toInt()
                tvCountDown.text = time.toString()
                countDown.progress = (60-time)*100/60
            }

            override fun onFinish() {
                val intent = Intent(this@RoleCheckActivity, MainHideSeek::class.java )
                startActivity(intent)
            }
        }.start()
    }

}