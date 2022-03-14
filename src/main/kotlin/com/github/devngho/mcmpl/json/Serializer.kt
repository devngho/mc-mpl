package com.github.devngho.mcmpl.json

import com.google.gson.JsonElement

object Serializer {
    val loc = LocationSerializer()
    val block = BlockSerializer()
    val player = PlayerSerializer()
}

interface SerializerObj<T> {
    fun serialize(value: T): JsonElement
}