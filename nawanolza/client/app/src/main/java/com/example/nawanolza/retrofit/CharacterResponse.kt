package com.example.nawanolza.retrofit

import java.io.Serializable

data class CharacterResponse(
    val collection : List<CollectionItem>
): Serializable {
    constructor(): this(ArrayList<CollectionItem>())
}
