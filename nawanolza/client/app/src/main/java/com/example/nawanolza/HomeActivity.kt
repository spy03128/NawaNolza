package com.example.nawanolza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.character.MapActivity
import com.example.nawanolza.createGame.GameIntro
import kotlinx.android.synthetic.main.activity_home.*

private const val TAG = "HomeActivity_싸피"
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        init()
    }
    
    private fun init() {
        characterButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, MapActivity::class.java)

            startActivity(intent)
        }

        gameButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, GameIntro::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
    }
}