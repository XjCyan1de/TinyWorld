package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getField
import com.github.xjcyan1de.cyanlibz.reflection.getMethod

inline class ChunkSection(val handle: Any) {
    val yPos: Int
        get() = yPosField[handle] as Int
    val data: DataPaletteBlock
        get() = DataPaletteBlock(
            blockIdsField[handle]
        )

    fun recalcBlockCounts() {
        recalcBlockCountsMethod.invoke(handle)
    }

    companion object {
        val clazz = nms("ChunkSection")
        val blockIdsField = getField(clazz, "blockIds")
        val yPosField = getField(clazz, "yPos")
        val recalcBlockCountsMethod = getMethod(
            clazz, "recalcBlockCounts"
        )
    }
}