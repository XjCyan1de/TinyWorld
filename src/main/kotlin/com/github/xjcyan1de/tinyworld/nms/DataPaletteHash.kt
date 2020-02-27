package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getField

inline class DataPaletteHash(override val handle: Any) :
    DataPalette {
    override val registry: RegistryBlockID
        get() = RegistryBlockID(
            fieldA[handle]
        )
    val statePaletteMap: RegistryID
        get() = RegistryID(fieldB[handle])
    val bits: Int
        get() = fieldF[handle] as Int
    val size: Int
        get() = statePaletteMap.size

    companion object {
        val clazz = nms("DataPaletteHash")
        val fieldA = getField(clazz, "a")
        val fieldB = getField(clazz, "b")
        val fieldC = getField(clazz, "c")
        val fieldD = getField(clazz, "d")
        val fieldE = getField(clazz, "e")
        val fieldF = getField(clazz, "f")
    }
}