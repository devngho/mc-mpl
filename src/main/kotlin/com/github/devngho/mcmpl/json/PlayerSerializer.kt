package com.github.devngho.mcmpl.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.bukkit.entity.Player
import java.lang.reflect.Type

class PlayerSerializer : JsonSerializer<Player>, SerializerObj<Player> {
    override fun serialize(src: Player?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return if (src != null) serialize(src) else JsonObject()
    }

    override fun serialize(value: Player): JsonElement {
        val res = JsonObject()
        res.add("location", Serializer.loc.serialize(value.location))
        res.addProperty("uuid", value.uniqueId.toString())
        res.addProperty("name", value.name)
        return res
    }
}