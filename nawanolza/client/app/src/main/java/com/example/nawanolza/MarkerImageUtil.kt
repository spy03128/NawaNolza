package com.example.nawanolza

class MarkerImageUtil {

    companion object {
        val charMap = mapOf<String, Any>(
            "a1" to R.drawable.a1,
            "a2" to R.drawable.a2,
            "a3" to R.drawable.a3,
        )

        fun getImage(characterId : Long): Any? {
            return charMap.get("a" + characterId)
        }
    }

}