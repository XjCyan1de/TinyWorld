package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.util.world
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.io.core.readBytes
import kotlinx.io.streams.asInput
import kotlinx.io.streams.asOutput
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.XZInputStream
import org.tukaani.xz.XZOutputStream
import java.io.File
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
object TinyWorldCommand : org.bukkit.command.Command("tinyworld") {
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
                WorldSaver(sender.world).also {
                    sender.world.worldSaver = it
                }
                sender.sendMessage("start saving changes")
            }
            "save" -> {
                sender as Player
                val worldSaver = sender.world.worldSaver
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