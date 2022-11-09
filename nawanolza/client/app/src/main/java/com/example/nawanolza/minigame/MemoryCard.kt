package com.example.nawanolza.minigame

class MemoryCard(image: Int, isFaceUp: Boolean, isMatched: Boolean) {
    var imageId : Int
    var isFaceUp : Boolean
    var isMatched : Boolean

    init {
        this.imageId = image
        this.isFaceUp = isFaceUp
        this.isMatched = isMatched
    }


}