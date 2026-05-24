package com.hoshiyomi.payloadtoolkit

import java.io.File

/**
 * FileHelper — File-related utility functions.
 *
 * Extracted from MainActivity to reduce monolithic file size.
 */
object FileHelper {

    /**
     * Format a file size in human-readable form.
     */
    fun formatFileSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> String.format("%.1f KB", bytes / 1024.0)
            bytes < 1024 * 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024))
            else -> String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024))
        }
    }

    /**
     * Clean up orphaned image files from the input directory.
     * Removes .img files that are no longer tracked in the active list.
     */
    fun cleanupOrphanedImages(inputDir: File, activePaths: Collection<String>) {
        inputDir.listFiles()?.forEach { file ->
            if (file.name.endsWith(".img") && file.absolutePath !in activePaths) {
                file.delete()
            }
        }
    }
}
