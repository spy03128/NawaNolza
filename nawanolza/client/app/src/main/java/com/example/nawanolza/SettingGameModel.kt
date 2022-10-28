package com.example.nawanolza

import androidx.lifecycle.ViewModel

class SettingGameModel:ViewModel() {

    var hideTime = 0

    fun plus(){
        hideTime += 10
    }

    fun minus(){
        if(hideTime < 0){
            return
        }
        hideTime -= 10
    }

}