package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getMethod

inline class RegistryBlockID(val handle: Any) {

    fun getId(any: Any): Int = methodGetId.invoke(handle, any) as Int

    fun fromId(int: Int) = methodFromId
        .invoke(handle, int)

    companion object {
        val clazz = nms("RegistryBlockID")
        val methodFromId = getMethod(
            clazz, "fromId", Int::class.java
        )
        val methodGetId = getMethod(
            clazz, "getId", Any::class.java
        )
    }
}