package com.github.devngho.mcmpl.event

import com.github.devngho.mcmpl.mplplugin.MplPlugin
import com.google.gson.JsonArray
import java.util.UUID

object EventModifiers {
    val eventModifiers: MutableList<Pair<Pair<UUID, MplPlugin>, (MplPlugin, JsonArray) -> Any>> = mutableListOf()
}