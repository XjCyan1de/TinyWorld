package com.github.xjcyan1de.tinyworld.nms

import com.github.xjcyan1de.cyanlibz.reflection.getField
import com.github.xjcyan1de.cyanlibz.reflection.getMethod

inline class RegistryID(override val handle: Any) : NmsWrapper {
    val size: Int
        get() = fieldF[handle] as Int

    fun getId(value: Any): Int = methodGetId.invoke(handle, value) as Int
    fun fromId(value: Int): Any = methodFromId.invoke(handle, value)

    fun add(value: Any): Int = methodC
        .invoke(handle, value) as Int

    fun clear() {
        methodA.invoke(handle)
    }

    companion object {
        val clazz = nms("RegistryID")
        val fieldF = getField(clazz, "f")
        val methodGetId = getMethod(clazz, "getId", Any::class.java)
        val methodFromId = getMethod(clazz, "fromId", Int::class.java)
        val methodA = getMethod(clazz, "a")
        val methodC = getMethod(clazz, "c", Any::class.java)
    }
}