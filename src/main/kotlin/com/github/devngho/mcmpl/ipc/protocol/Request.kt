package com.github.devngho.mcmpl.ipc.protocol

import com.google.gson.JsonObject

@Suppress("unused")
class Request(val requestType: RequestType, val taskData: JsonObject?)