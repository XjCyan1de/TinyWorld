package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.world.generator.VoidGenerator
import com.github.xjcyan1de.tinyworld.nms.DataPaletteHash
import com.github.xjcyan1de.tinyworld.nms.DataPaletteLinear
import com.github.xjcyan1de.tinyworld.obc.getChunkSection
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

class TinyWorldGenerator(
    val chunks: Map<Long, Array<ChunkSectionData?>>,
    val generator: ChunkGenerator = VoidGenerator
) : ChunkGenerator() {
    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biomeGrid: BiomeGrid): ChunkData {
        val chunkData = createChunkData(world)
        val chunkKey = z.toLong() * Integer.MAX_VALUE + x.toLong()
        val chunk = chunks[chunkKey]
        if (chunk != null) {
            chunk.forEachIndexed { index, chunkSectionData ->
                if (chunkSectionData != null) {
                    try {
                        chunkData.loadSection(index, chunkSectionData)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            return generator.generateChunkData(world, random, x, z, biomeGrid)
        }
        return chunkData
    }

    fun ChunkData.loadSection(index: Int, chunkSectionData: ChunkSectionData) {
        val chunkSection = getChunkSection(index shl 4)
        val dataPaletteBlock = chunkSection.data
        dataPaletteBlock.setBits(chunkSectionData.bits.toInt())
        chunkSectionData.data.copyInto(dataPaletteBlock.storage.backingLongArray)

        val palette = dataPaletteBlock.palette
        val registry = palette.registry

        when (palette) {
            is DataPaletteHash -> {
                palette.statePaletteMap.clear()
                chunkSectionData.palette.forEach { id ->
                    palette.statePaletteMap.add(registry.fromId(id))
                }
            }
            is DataPaletteLinear -> {
                palette.size = chunkSectionData.palette.size
                chunkSectionData.palette.forEachIndexed { i, id ->
                    palette.states[i] = registry.fromId(id)
                }
            }
            else -> error("Unknown Palette Type: ${palette::class}")
        }

        chunkSection.recalcBlockCounts()
    }
}