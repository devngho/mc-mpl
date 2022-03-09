package com.github.devngho.mcmpl

import com.github.devngho.mcmpl.ipc.Ipc
import com.github.devngho.mcmpl.ipc.protocol.TaskType
import com.github.devngho.mcmpl.mplplugin.MplPluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Plugin : JavaPlugin(){
    companion object {
        var isEnabled = false
    }
    override fun onEnable() {
        super.onEnable()
        Companion.isEnabled = true
        TaskType.OnEnable.createTask(UUID.randomUUID(), null).fire()
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