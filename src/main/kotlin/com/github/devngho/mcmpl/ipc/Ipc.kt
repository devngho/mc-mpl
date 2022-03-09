package com.github.devngho.mcmpl.ipc

import com.github.devngho.mcmpl.mplplugin.MplPlugin
import com.github.devngho.mcmpl.mplplugin.MplPluginManager.plugins
import org.bukkit.Bukkit
import java.io.File
import kotlin.concurrent.thread

object Ipc {
    fun initPlugin(file: File){
        thread {
            val process = ProcessBuilder().directory(file.parentFile.absoluteFile).command(file.absolutePath).start()
            val plugin = MplPlugin(process, process.outputStream.buffered(), process.inputStream.buffered(), file.nameWithoutExtension)
            plugins.add(plugin)
            Bukkit.getLogger().info("Loading mc-mpl plugin ${file.nameWithoutExtension}")
            plugin.createWaiter()
        }
    }
}