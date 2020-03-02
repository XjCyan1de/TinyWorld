package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.world.generator.VoidGenerator
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import kotlinx.io.core.ByteReadPacket
import org.bukkit.World
import org.bukkit.WorldCreator
import java.util.*
import kotlin.math.ceil

class WorldLoader(
    val name: String,
    private val byteArray: ByteArray
) : WorldCreator(name) {
    override fun createWorld(): World? = ByteReadPacket(byteArray).run {
        val minX = readVarInt()
        val minZ = readVarInt()
        val width = readVarInt()
        val depth = readVarInt()
        val bitSetSize = ceil((width * depth) / 8.0).toInt()
        val chunkBitSet = readBitSet(bitSetSize)

        val chunkMap = Long2ObjectOpenHashMap<Array<ChunkSectionData?>>()
        val chunkCoords = LinkedList<Pair<Int, Int>>()
        for (x in 0 until width) {
            for (z in 0 until depth) {
                val index = x * width + z
                if (chunkBitSet[index]) {
                    chunkCoords.add(minX + x to minZ + z)
                    chunkMap[(minX.toLong() + x) * Integer.MAX_VALUE + (minZ.toLong() + z)] = readChunkData()
                }
            }
        }

        val currentGenerator = generator() ?: VoidGenerator
        generator(TinyWorldGenerator(chunkMap, currentGenerator))
        val world = super.createWorld()!!
        generator(currentGenerator)
        world.worldSaver = WorldSaver(world).apply {
            chunkCoords.forEach { handleChunkEdit(world.getChunkAt(it.first, it.second)) }
        }
        return world
    }
}