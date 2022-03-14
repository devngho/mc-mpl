package com.github.devngho.mcmpl.event

import com.github.devngho.mcmpl.ipc.protocol.TaskType
import com.github.devngho.mcmpl.json.Serializer
import com.github.devngho.mcmpl.mplplugin.MplPlugin
import com.github.devngho.mcmpl.mplplugin.MplPluginManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockCanBuildEvent
import java.util.*
import java.util.concurrent.CountDownLatch

class BlockEvent : Listener {
    @EventHandler
    fun onBlockBreakEvent(ev: BlockBreakEvent){
        val obj = JsonObject()
        obj.addProperty("type", ev.eventName)
        obj.add("block", Serializer.block.serialize(ev.block))
        obj.add("player", Serializer.player.serialize(ev.player))
        val uuid = UUID.randomUUID()
        TaskType.Event.createTask(uuid, obj).fire()
        val latch = CountDownLatch(MplPluginManager.plugins.count { it.registeredEvents.contains(ev.eventName) })
        MplPluginManager.plugins.filter { it.registeredEvents.contains(ev.eventName) }.forEach {
            EventModifiers.eventModifiers.add(Pair(Pair(uuid, it)) { _: MplPlugin, jsonObject: JsonArray ->
                jsonObject.forEach { e ->
                    if (e.asJsonObject.get("type").asString == "cancelled"){
                        ev.isCancelled = e.asJsonObject.get("data").asBoolean
                    }
                }
                latch.countDown()
            })
        }
        latch.await()
    }
    @EventHandler
    fun onBlockBurnEvent(ev: BlockBurnEvent){
        val obj = JsonObject()
        obj.addProperty("type", ev.eventName)
        obj.add("block", Serializer.block.serialize(ev.block))
        val ig = ev.ignitingBlock
        if (ig != null) obj.add("ignitingBlock", Serializer.block.serialize(ig))
        val uuid = UUID.randomUUID()
        TaskType.Event.createTask(uuid, obj).fire()
        val latch = CountDownLatch(MplPluginManager.plugins.count { it.registeredEvents.contains(ev.eventName) })
        MplPluginManager.plugins.filter { it.registeredEvents.contains(ev.eventName) }.forEach {
            EventModifiers.eventModifiers.add(Pair(Pair(uuid, it)) { _: MplPlugin, jsonObject: JsonArray ->
                jsonObject.forEach { e ->
                    if (e.asJsonObject.get("type").asString == "cancelled"){
                        ev.isCancelled = e.asJsonObject.get("data").asBoolean
                    }
                }
                latch.countDown()
            })
        }
        latch.await()
    }
    @EventHandler
    fun onCanBuildEvent(ev: BlockCanBuildEvent){
        val obj = JsonObject()
        obj.addProperty("type", ev.eventName)
        obj.add("block", Serializer.block.serialize(ev.block))
        val p = ev.player
        if (p != null) obj.add("player", Serializer.player.serialize(p))
        val uuid = UUID.randomUUID()
        TaskType.Event.createTask(uuid, obj).fire()
    }
}