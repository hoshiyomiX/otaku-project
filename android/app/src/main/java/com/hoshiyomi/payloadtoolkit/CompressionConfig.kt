package com.hoshiyomi.payloadtoolkit

/**
 * CompressionConfig — Centralised compression algorithm configuration.
 *
 * Single source of truth for:
 *   - Algorithm names exposed in the UI
 *   - Level ranges per algorithm (Kotlin-side)
 *   - Level item generation for spinner adapters
 */
object CompressionConfig {

    /** Compression level ranges per algorithm: (min, max). */
    val LEVEL_RANGES: Map<String, Pair<Int, Int>> = mapOf(
        "none" to Pair(0, 0),     // no compression
        "gzip" to Pair(1, 9),     // stdlib gzip: levels 1-9
        "bzip2" to Pair(1, 9),    // stdlib bzip2: levels 1-9
        "xz" to Pair(0, 9),       // stdlib lzma: presets 0-9 (7-9 slow on mobile)
        "brotli" to Pair(0, 11)   // brotli package: quality 0-11
    )

    /**
     * Build the list of level values for a compression algorithm's spinner.
     *
     * Position 0 is always -1 (mapped to "Default (best)" label).
     * Subsequent values are the actual level range (min..max).
     *
     * Example for xz: [-1, 0, 1, 2, ..., 9] → labels ["Default (best)", "0", "1", ..., "9"]
     * Example for none: [0] → label ["Default"]
     */
    fun getLevelItems(algorithm: String): List<Int> {
        val range = LEVEL_RANGES[algorithm] ?: (0 to 0)
        val (min, max) = range
        return if (min == 0 && max == 0) {
            listOf(0)  // "none" — just show "Default"
        } else {
            listOf(-1) + (min..max).toList()
        }
    }

    /**
     * Format a level value to a human-readable spinner label.
     */
    fun formatLevelLabel(level: Int): String {
        return if (level == -1) "Default (best)" else "$level"
    }

    /**
     * Format a level value for display in the log header.
     * Returns empty string for default (-1).
     */
    fun formatLevelForLog(level: Int): String {
        return if (level < 0) "" else " (level $level)"
    }
}
