package com.github.devngho.mcmpl.ipc.protocol

class TaskDataException(msg: String?) : Exception("Task data is not valid: $msg")
class RequestDataException(msg: String?) : Exception("Request data is not valid: $msg")