package com.github.xjcyan1de.tinyworld

import kotlinx.io.core.Input
import kotlinx.io.core.Output
import kotlinx.io.core.readBytes
import kotlinx.io.core.writeFully
import java.io.IOException
import java.util.*

fun Input.readVarInt(): Int {
    var value = 0
    var size = 0
    var b: Int
    while (true) {
        b = readByte().toInt()
        if (b and 0x80 != 0x80) break
        value = value or (b and 0x7F shl size++ * 7)
        if (size > 5) {
            throw IOException("VarInt too long (length must be <= 5)")
        }
    }
    return value or (b and 0x7F shl size * 7)
}

fun Output.writeVarInt(int: Int) {
    var i = int
    while (i and 0x7F.inv() != 0) {
        writeByte((i and 0x7F or 0x80).toByte())
        i = i ushr 7
    }
    writeByte(i.toByte())
}

fun Input.readBitSet(fixedSize: Int) = BitSet.valueOf(readBytes(fixedSize))

fun Output.writeBitSet(bitSet: BitSet, fixedSize: Int) {
    val array = bitSet.toByteArray()
    writeFully(array)
    val chunkMaskPadding = fixedSize - array.size
    for (i in 0 until chunkMaskPadding) {
        writeByte(0)
    }
}

fun Input.readChunkData(): Array<ChunkSectionData?> {
    val bitSet = readBitSet(2)
    return Array(16) {
        if (bitSet[it]) {
            val bits = readByte()
            val palette = IntArray(readVarInt()) {
                readVarInt()
            }
            val data = LongArray(readVarInt()) {
                readLong()
            }
            ChunkSectionData(bits, palette, data)
        } else null
    }
}

fun Output.writeChunkData(sections: Array<ChunkSectionData?>) {
    val bitSet = BitSet(16)
    sections.forEachIndexed { index, section ->
        bitSet[index] = section != null
    }
    writeBitSet(bitSet, 2)
    sections.forEach { section ->
        if (section != null) {
            writeByte(section.bits)
            writeVarInt(section.palette.size)
            section.palette.forEach {
                writeVarInt(it)
            }
            writeVarInt(section.data.size)
            section.data.forEach {
                writeLong(it)
            }
        }
    }
}