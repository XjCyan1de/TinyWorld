package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getField
import com.github.xjcyan1de.cyanlibz.reflection.getMethod


inline class DataPaletteBlock(val handle: Any) {
    val storage: DataBits
        get() = DataBits(fieldA[handle])

    val palette: DataPalette
        get() {
            val value = fieldH[handle]
            val valueClass = value.javaClass
            return when {
                DataPaletteLinear
                    .clazz == valueClass -> DataPaletteLinear(value)
                DataPaletteHash
                    .clazz == valueClass -> DataPaletteHash(value)
                else -> error(":c")
            }
        }

    fun setBits(value: Int) = methodB
        .invoke(handle, value)

    companion object {
        val clazz = nms("DataPaletteBlock")
        val fieldA = getField(clazz, "a")
        val fieldB = getField(clazz, "b")
        val fieldC = getField(clazz, "c")
        val fieldD = getField(clazz, "d")
        val fieldE = getField(clazz, "e")
        val fieldF = getField(clazz, "f")
        val fieldH = getField(clazz, "h")
        val methodB = getMethod(clazz, "b", Int::class.java)
    }
}