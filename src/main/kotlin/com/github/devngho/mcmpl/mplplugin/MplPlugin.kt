package com.github.devngho.mcmpl.mplplugin

import com.github.devngho.mcmpl.Plugin
import com.github.devngho.mcmpl.ipc.protocol.RequestType
import com.github.devngho.mcmpl.ipc.protocol.Task
import com.github.devngho.mcmpl.ipc.protocol.TaskType
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.util.*
import kotlin.concurrent.thread

class MplPlugin(
    val process: Process,
    val out: BufferedOutputStream,
    val inp: BufferedInputStream,
    val name: String
) {
    val registeredTasks = mutableListOf<TaskType>()
    val registeredEvents = mutableListOf<String>()
    fun sendTask(task: Task){
        if (registeredTasks.contains(task.taskType) && (task.taskType != TaskType.Event || registeredEvents.contains(task.taskData?.get("type")?.asString))) {
            val send = JsonObject()
            send.addProperty("type", task.taskType.name)
            send.addProperty("id", task.taskId.toString())
            send.add("data", task.taskData)
            if (task.taskData != null) send.add("data", Gson().toJsonTree(task.taskData))
            with(out.bufferedWriter()) {
                write(send.toString())
                newLine()
                flush()
            }
        }
    }
    fun createWaiter(){
        thread {
            var isFirst = true
            var isFirstSent = false
            while (true){
                if (inp.available() > 0){
                    isFirstSent = true
                    val reader = JsonReader(inp.bufferedReader())
                    reader.isLenient = true
                    val data = JsonParser.parseReader(reader).asJsonObject
                    val requestType = RequestType.valueOf(data.get("type").asString)
                    requestType.executeRequest(this@MplPlugin, data.getAsJsonObject("data"))
                }
                if (Plugin.isEnabled && isFirst && isFirstSent) {
                    this.sendTask(TaskType.OnEnable.createTask(UUID.randomUUID(), null))
                    isFirst = false
                }
            }
        }
    }
}