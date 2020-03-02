package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.metadata.metadata
import com.github.xjcyan1de.cyanlibz.metadata.MetadataKey
import org.bukkit.World

internal val worldSaverMetadataKey = MetadataKey<WorldSaver>("tinyworld.world_saver")

private val worldListener = WorldListener

var World.worldSaver: WorldSaver?
    get() = metadata[worldSaverMetadataKey]
    set(value) {
        if (value != null) {
            metadata[worldSaverMetadataKey] = value
        } else {
            metadata.remove(worldSaverMetadataKey)
        }
    }