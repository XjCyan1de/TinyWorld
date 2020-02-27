package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.world.generator.VoidGenerator
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import kotlinx.io.core.ByteReadPacket
import org.bukkit.World
import org.bukkit.WorldCreator
import kotlin.math.ceil

class WorldLoader(
    name: String,
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
        for (z in 0 until depth) {
            for (x in 0 until width) {
                val index = z * width + x
                if (chunkBitSet[index]) {
                    chunkMap[(minZ.toLong() + z) * Integer.MAX_VALUE + (minX.toLong() + x)] = readChunkData()
                }
            }
        }
        val currentGenerator = generator() ?: VoidGenerator
        generator(TinyWorldGenerator(chunkMap, currentGenerator))
        val world = super.createWorld()
        generator(currentGenerator)
        return world
    }
}