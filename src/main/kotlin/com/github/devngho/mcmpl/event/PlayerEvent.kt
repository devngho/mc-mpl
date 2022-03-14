package com.github.devngho.mcmpl.event

import com.github.devngho.mcmpl.ipc.protocol.TaskType
import com.github.devngho.mcmpl.json.Serializer
import com.google.gson.JsonObject
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PlayerEvent : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent){
        val obj = JsonObject()
        obj.addProperty("type", e.eventName)
        obj.add("player", Serializer.player.serialize(e.player))
        TaskType.Event.createTask(UUID.randomUUID(), obj).fire()
    }
    @EventHandler
    fun onExit(e: PlayerQuitEvent){
        val obj = JsonObject()
        obj.addProperty("type", e.eventName)
        obj.add("player", Serializer.player.serialize(e.player))
        TaskType.Event.createTask(UUID.randomUUID(), obj).fire()
    }
}