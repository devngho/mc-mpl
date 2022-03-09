package com.github.devngho.mcmpl.ipc.protocol

import com.github.devngho.mcmpl.mplplugin.MplPlugin
import com.google.gson.JsonObject
import org.bukkit.Bukkit

enum class RequestType {
    RegisterTask {
        override fun executeRequest(plugin: MplPlugin, requestData: JsonObject?) {
            if (requestData == null) throw RequestDataException("Register task data is null.")
            if (!requestData.has("type")) throw RequestDataException("Register task data is wrong.")
            plugin.registeredTasks.add(TaskType.valueOf(requestData.get("type").asString))
        }
    },
    Log {
        override fun executeRequest(plugin: MplPlugin, requestData: JsonObject?) {
            if (requestData == null) throw RequestDataException("Log data is null.")
            if (!requestData.has("message")) throw RequestDataException("Log data is wrong.")
            Bukkit.getLogger().info("[mc-mpl ${plugin.name}] ${requestData.get("message").asString}")
        }
    }
    ;
    abstract fun executeRequest(plugin: MplPlugin, requestData: JsonObject?)
}