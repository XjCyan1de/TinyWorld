@file:Suppress("NOTHING_TO_INLINE")

package com.github.xjcyan1de.tinyworld.nms

import org.bukkit.Bukkit

val NMS_VERSION by lazy {
    Bukkit.getServer().javaClass.getPackage().name.substring(23)
}

fun nms(name: String) =
    Class.forName("net.minecraft.server.$NMS_VERSION.$name")

fun obc(name: String) =
    Class.forName("org.bukkit.craftbukkit.$NMS_VERSION.$name")