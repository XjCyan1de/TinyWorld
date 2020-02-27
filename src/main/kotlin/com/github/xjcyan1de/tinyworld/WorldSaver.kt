package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.terminable.Terminable
import com.github.xjcyan1de.tinyworld.nms.DataPaletteHash
import com.github.xjcyan1de.tinyworld.nms.DataPaletteLinear
import com.github.xjcyan1de.tinyworld.obc.blockIds
import kotlinx.io.core.Output
import org.bukkit.Chunk
import org.bukkit.ChunkSnapshot
import org.bukkit.World
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.ceil

class WorldSaver(
    val world: World
) : Terminable {
    private val editedChunks = HashSet<Chunk>()
    val chunksCount: Int
        get() = editedChunks.size

    fun save(output: Output) = output.apply {
        val sortedChunks =
            editedChunks.map { it.chunkSnapshot }.sortedBy { it.z.toLong() * Integer.MAX_VALUE + it.x.toLong() }
        val minX = sortedChunks.map { it.x }.toIntArray().min() ?: 0
        val minZ = sortedChunks.map { it.z }.toIntArray().min() ?: 0
        val maxX = sortedChunks.map { it.x }.toIntArray().max() ?: 0
        val maxZ = sortedChunks.map { it.z }.toIntArray().max() ?: 0
        val width = maxX - minX + 1
        val depth = maxZ - minZ + 1
        val chunkBitSetSize = ceil((width * depth) / 8.0).toInt()
        val chunkBitSet = BitSet(width * depth)
        for (chunk in sortedChunks) {
            val index = (chunk.z - minZ) * width + (chunk.x - minX)
            chunkBitSet[index] = true
        }

        writeVarInt(minX)
        writeVarInt(minZ)
        writeVarInt(width)
        writeVarInt(depth)
        writeBitSet(chunkBitSet, chunkBitSetSize)

        for (chunk in sortedChunks) {
            val sections = Array(16) {
                if (!chunk.isSectionEmpty(it)) {
                    chunk.saveSection(it)
                } else null
            }
            writeChunkData(sections)
        }
    }

    private fun ChunkSnapshot.saveSection(index: Int): ChunkSectionData {
        val dataPaletteBlock = blockIds[index]

        val dataBits = dataPaletteBlock.storage
        val longArray = dataBits.backingLongArray
        val bits = dataBits.bitsPerEntry

        val palette = dataPaletteBlock.palette
        val registry = palette.registry
        val intArray = when (palette) {
            is DataPaletteHash -> IntArray(palette.size) {
                registry.getId(palette.statePaletteMap.fromId(it))
            }
            is DataPaletteLinear -> IntArray(palette.size) {
                registry.getId(palette.states[it])
            }
            else -> error("Unknown DataPalette type: ${palette.javaClass}")
        }
        return ChunkSectionData(bits.toByte(), intArray, longArray)
    }

    fun handleChunkEdit(chunk: Chunk) {
        editedChunks.add(chunk)
    }

    override fun close() {
        editedChunks.clear()
    }
}