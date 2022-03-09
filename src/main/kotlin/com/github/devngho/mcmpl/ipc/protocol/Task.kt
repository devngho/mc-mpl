package com.github.devngho.mcmpl.ipc.protocol

import com.github.devngho.mcmpl.mplplugin.MplPluginManager
import com.google.gson.JsonObject
import java.util.UUID

@Suppress("unused")
class Task constructor(val taskId: UUID, val taskType: TaskType, val taskData: JsonObject?) {
    fun fire() {
        MplPluginManager.plugins.forEach {
            it.sendTask(this)
        }
    }
}