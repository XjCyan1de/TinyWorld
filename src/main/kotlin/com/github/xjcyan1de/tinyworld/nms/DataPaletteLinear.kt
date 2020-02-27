package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getField

@Suppress("UNCHECKED_CAST")
inline class DataPaletteLinear(override val handle: Any) :
    DataPalette {
    override val registry: RegistryBlockID
        get() = RegistryBlockID(
            fieldA[handle]
        )
    val states: Array<Any>
        get() = fieldB[handle] as Array<Any>
    var size: Int
        get() = fieldF[handle] as Int
        set(value) {
            fieldF[handle] = value
        }

    companion object {
        val clazz = nms("DataPaletteLinear")
        val fieldA = getField(clazz, "a")
        val fieldB = getField(clazz, "b")
        val fieldC = getField(clazz, "c")
        val fieldD = getField(clazz, "d")
        val fieldE = getField(clazz, "e")
        val fieldF = getField(clazz, "f")
    }
}