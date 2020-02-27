package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getConstructor
import com.github.xjcyan1de.cyanlibz.reflection.getField

inline class DataBits(val handle: Any) {
    inline val backingLongArray: LongArray
        get() = fieldA[handle] as LongArray
    inline val bitsPerEntry: Int
        get() = fieldB[handle] as Int
    inline val maxEntryValue: Long
        get() = fieldC[handle] as Long
    inline val arraySize: Int
        get() = fieldD[handle] as Int

    companion object {
        val clazz = nms("DataBits")
        val constructorA = getConstructor(
            clazz, Int::class.java, Int::class.java
        )
        val constructorB = getConstructor(
            clazz, Int::class.java, Int::class.java, LongArray::class.java
        )
        val fieldA = getField(clazz, "a")
        val fieldB = getField(clazz, "b")
        val fieldC = getField(clazz, "c")
        val fieldD = getField(clazz, "d")

        operator fun invoke(bitsPerEntry: Int, arraySize: Int): DataBits =
            DataBits(
                constructorA
                    .newInstance(bitsPerEntry, arraySize)
            )

        operator fun invoke(bitsPerEntry: Int, arraySize: Int, longArray: LongArray) =
            DataBits(
                constructorB
                    .newInstance(bitsPerEntry, arraySize, longArray)
            )
    }
}