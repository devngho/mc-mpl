package com.github.devngho.mcmpl.ipc.protocol

import com.google.gson.JsonObject
import java.util.UUID

enum class TaskType {
    OnEnable {
        override fun createTask(taskId: UUID, taskData: JsonObject?): Task {
            if (taskData != null) throw TaskDataException("OnEnable data is not null.")
            return Task(taskId, this, null)
        }
    },
    Event {
        override fun createTask(taskId: UUID, taskData: JsonObject?): Task {
            if (taskData == null) throw TaskDataException("Event data is null.")
            return Task(taskId, this, taskData)
        }
    }
    ;
    abstract fun createTask(taskId: UUID, taskData: JsonObject?): Task
}