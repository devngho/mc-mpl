package com.github.devngho.mcmpl.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.bukkit.Location
import java.lang.reflect.Type

class LocationSerializer : JsonSerializer<Location>, SerializerObj<Location> {
    override fun serialize(src: Location?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return if (src != null) serialize(src) else JsonObject()
    }

    override fun serialize(value: Location): JsonElement {
        val res = JsonObject()
        res.addProperty("x", value.x)
        res.addProperty("y", value.y)
        res.addProperty("z", value.z)
        res.addProperty("world", value.world.name)
        res.addProperty("yaw", value.yaw)
        res.addProperty("pitch", value.pitch)
        return res
    }
}