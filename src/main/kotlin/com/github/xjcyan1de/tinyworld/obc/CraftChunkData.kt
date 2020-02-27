@file:Suppress("NOTHING_TO_INLINE")

package com.github.xjcyan1de.tinyworld.obc

import com.github.xjcyan1de.cyanlibz.reflection.getField
import com.github.xjcyan1de.cyanlibz.reflection.getMethod
import com.github.xjcyan1de.tinyworld.nms.ChunkSection
import org.bukkit.generator.ChunkGenerator

inline val ChunkGenerator.ChunkData.sections: Array<ChunkSection?>
    get() = (getField(javaClass, "sections")[this] as Array<*>).map {
        ChunkSection(it!!)
    }.toTypedArray()

inline fun ChunkGenerator.ChunkData.getChunkSection(y: Int, create: Boolean = true): ChunkSection =
    ChunkSection(
        getMethod(javaClass, "getChunkSection", Int::class.java, Boolean::class.java).invoke(
            this,
            y,
            create
        )
    )