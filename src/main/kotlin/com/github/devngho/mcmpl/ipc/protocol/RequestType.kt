package com.github.devngho.mcmpl.ipc.protocol

import com.github.devngho.mcmpl.event.EventModifiers
import com.github.devngho.mcmpl.mplplugin.MplPlugin
import com.google.gson.JsonObject
import org.bukkit.Bukkit
import java.util.UUID

enum class RequestType {
    RegisterTask {
        override fun executeRequest(plugin: MplPlugin, requestData: JsonObject?) {
            if (requestData == null) throw RequestDataException("Register task data is null.")
            if (!requestData.has("type")) throw RequestDataException("Register task data is wrong.")
            plugin.registeredTasks.add(TaskType.valueOf(requestData.get("type").asString))
        }
    },
    RegisterEvent {
        override fun executeRequest(plugin: MplPlugin, requestData: JsonObject?) {
            if (requestData == null) throw RequestDataException("Register event data is null.")
            if (!requestData.has("type")) throw RequestDataException("Register event data is wrong.")
            plugin.registeredEvents.add(requestData.get("type").asString)
        }
    },
    Log {
        override fun executeRequest(plugin: MplPlugin, requestData: JsonObject?) {
            if (requestData == null) throw RequestDataException("Log data is null.")
            if (!requestData.has("message")) throw RequestDataException("Log data is wrong.")
            Bukkit.getLogger().info("[mc-mpl ${plugin.name}] ${requestData.get("message").asString}")
        }
    },
    ModifyEvent {
        override fun executeRequest(plugin: MplPlugin, requestData: JsonObject?) {
            if (requestData == null) throw RequestDataException("Event modify data is null.")
            if (!requestData.has("modifiers")) throw RequestDataException("Event modifiers is wrong.")
            val uuid = UUID.fromString(requestData.get("uuid").asString)
            EventModifiers.eventModifiers.filter { it.first.first == uuid && it.first.second == plugin }.forEach {
                it.second(plugin, requestData.getAsJsonArray("modifiers"))
            }
        }
    }
    ;
    abstract fun executeRequest(plugin: MplPlugin, requestData: JsonObject?)
}