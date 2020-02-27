package com.github.xjcyan1de.tinyworld.obc

import com.github.xjcyan1de.cyanlibz.reflection.getField
import com.github.xjcyan1de.tinyworld.nms.DataPaletteBlock
import org.bukkit.ChunkSnapshot

inline val ChunkSnapshot.blockIds: Array<DataPaletteBlock>
    get() = (getField(javaClass, "blockids")[this] as Array<*>).map {
        DataPaletteBlock(it!!)
    }.toTypedArray()