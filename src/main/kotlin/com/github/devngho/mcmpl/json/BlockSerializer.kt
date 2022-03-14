package com.github.devngho.mcmpl.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.bukkit.block.Block
import java.lang.reflect.Type

class BlockSerializer : JsonSerializer<Block>, SerializerObj<Block> {
    override fun serialize(src: Block?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return if (src != null) serialize(src) else JsonObject()
    }

    override fun serialize(value: Block): JsonElement {
        val res = JsonObject()
        res.add("location", Serializer.loc.serialize(value.location))
        res.addProperty("type", value.type.toString())
        return res
    }
}