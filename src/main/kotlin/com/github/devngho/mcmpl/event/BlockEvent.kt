package com.github.devngho.mcmpl.event

import com.github.devngho.mcmpl.ipc.protocol.TaskType
import com.github.devngho.mcmpl.json.Serializer
import com.google.gson.JsonObject
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockCanBuildEvent
import java.util.*

class BlockEvent : Listener {
    @EventHandler
    fun onBlockBreakEvent(e: BlockBreakEvent){
        val obj = JsonObject()
        obj.addProperty("type", e.eventName)
        obj.add("block", Serializer.block.serialize(e.block))
        obj.add("player", Serializer.player.serialize(e.player))
        val uuid = UUID.randomUUID()
        TaskType.Event.createTask(uuid, obj).fire()
    }
    @EventHandler
    fun onBlockBurnEvent(e: BlockBurnEvent){
        val obj = JsonObject()
        obj.addProperty("type", e.eventName)
        obj.add("block", Serializer.block.serialize(e.block))
        val ig = e.ignitingBlock
        if (ig != null) obj.add("ignitingBlock", Serializer.block.serialize(ig))
        TaskType.Event.createTask(UUID.randomUUID(), obj).fire()
    }
    @EventHandler
    fun onCanBuildEvent(e: BlockCanBuildEvent){
        val obj = JsonObject()
        obj.addProperty("type", e.eventName)
        obj.add("block", Serializer.block.serialize(e.block))
        val p = e.player
        if (p != null) obj.add("player", Serializer.player.serialize(p))
        TaskType.Event.createTask(UUID.randomUUID(), obj).fire()
    }
}