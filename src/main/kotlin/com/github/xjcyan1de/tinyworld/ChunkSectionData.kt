package com.github.xjcyan1de.tinyworld

data class ChunkSectionData(
    val bits: Byte,
    val palette: IntArray,
    val data: LongArray
) {

    override fun toString(): String {
        return "ChunkSectionData(bits=$bits, palette=${palette.contentToString()}, data=${data.contentToString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChunkSectionData

        if (bits != other.bits) return false
        if (!data.contentEquals(other.data)) return false
        if (!palette.contentEquals(other.palette)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bits.toInt()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + palette.contentHashCode()
        return result
    }
}