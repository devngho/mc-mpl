package com.github.devngho.mcmpl

import com.github.devngho.mcmpl.event.BlockEvent
import com.github.devngho.mcmpl.event.PlayerEvent
import com.github.devngho.mcmpl.ipc.Ipc
import com.github.devngho.mcmpl.ipc.protocol.TaskType
import com.github.devngho.mcmpl.mplplugin.MplPluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Plugin : JavaPlugin(){
    companion object {
        var isEnabled = false
        lateinit var plugin: Plugin
    }
    override fun onEnable() {
        super.onEnable()
        Companion.isEnabled = true
        plugin = this
        TaskType.OnEnable.createTask(UUID.randomUUID(), null).fire()
        server.pluginManager.registerEvents(BlockEvent(), this)
        server.pluginManager.registerEvents(PlayerEvent(), this)
    }

    override fun onLoad() {
        super.onLoad()
        if (!dataFolder.exists()) dataFolder.mkdir()
        dataFolder.walk().forEach {
            if (it.isFile) Ipc.initPlugin(it)
        }
    }

    override fun onDisable() {
        super.onDisable()
        MplPluginManager.plugins.forEach {
            it.process.destroy()
        }
    }
}