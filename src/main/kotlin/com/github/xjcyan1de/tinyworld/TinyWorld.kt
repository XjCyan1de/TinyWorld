package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.BukkitPlugin
import com.github.xjcyan1de.cyanlibz.bukkit.util.initialize
import com.github.xjcyan1de.cyanlibz.bukkit.util.world
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.io.core.readBytes
import kotlinx.io.streams.asInput
import kotlinx.io.streams.asOutput
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.XZInputStream
import org.tukaani.xz.XZOutputStream
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val worldSavers = HashMap<World, WorldSaver>()

@UseExperimental(ExperimentalTime::class)
class TinyWorld : BukkitPlugin() {
    override fun enable() {
        initialize()
        server.commandMap.knownCommands["tinyworld"] = Command()
        server.pluginManager.registerEvents(WorldListener, this)
    }

    inner class Command : org.bukkit.command.Command("tinyworld") {
        init {
            permission = "tinyworld.admin"
        }

        override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
            if (args.isEmpty()) {
                sender.sendMessage(usageMessage)
                return true
            }
            when (args[0]) {
                "record" -> {
                    sender as Player
                    val worldSaver = worldSavers.getOrPut(sender.world) {
                        WorldSaver(sender.world)
                    }
                    worldSaver.close()
                    sender.sendMessage("start saving changes")
                }
                "save" -> {
                    sender as Player
                    val worldSaver = worldSavers[sender.world]
                    if (worldSaver != null) {
                        GlobalScope.launch {
                            val file = File(args[1] + ".tyw")
                            val time = measureTime {
                                XZOutputStream(file.outputStream(), LZMA2Options()).asOutput().use {
                                    worldSaver.save(it)
                                }
                            }
                            sender.sendMessage("-> saved $file (${worldSaver.chunksCount} chunks, ${file.length()} bytes) in $time")
                        }
                    }
                }
                "load" -> {
                    sender as Player
                    val file = File(args[1] + ".tyw")
                    if (file.exists()) {
                        val uuid = UUID.randomUUID().toString()
                        val loader = XZInputStream(file.inputStream()).asInput().use {
                            WorldLoader(uuid, it.readBytes())
                        }
                        val world = world(uuid, loader)
                        sender.teleport(Location(world, 0.5, 100.1, 0.5))
                    }
                }
            }
            return true
        }
    }
}