package com.hoshiyomi.payloadtoolkit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView

/**
 * LogHelper — Centralised log display and clipboard utilities.
 *
 * Extracted from MainActivity to reduce monolithic file size.
 */
object LogHelper {

    enum class LogLevel(val tag: String, val colorRes: Int) {
        INFO("INFO", R.color.log_info),
        WARN("WARN", R.color.log_warning),
        ERROR("ERR ", R.color.log_error),
        SUCCESS("OK  ", R.color.log_success),
        PLAIN("", 0),
    }

    /**
     * Append text to the log TextView with optional color-coded prefix.
     */
    fun showLog(
        context: Context,
        textView: TextView?,
        text: String,
        level: LogLevel = LogLevel.PLAIN,
        scrollView: NestedScrollView? = null
    ) {
        val tv = textView ?: return
        if (level == LogLevel.PLAIN) {
            tv.append(text)
        } else {
            val prefix = "[${level.tag}] "
            val colored = SpannableString("$prefix$text")
            try {
                colored.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(context, level.colorRes)
                    ),
                    0, prefix.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } catch (_: Exception) { /* fallback to plain */ }
            tv.append(colored)
        }
        // Scroll to bottom. Programmatic smoothScrollTo does NOT dispatch
        // nested scroll events, so it won't trigger the parent NestedScrollView.
        scrollView?.post {
            val child = scrollView.getChildAt(0)
            if (child != null) {
                val target = child.bottom - scrollView.height
                scrollView.smoothScrollTo(0, if (target > 0) target else 0)
            }
        }
    }

    /**
     * Copy log text to clipboard with a Toast confirmation.
     */
    fun copyLogToClipboard(context: Context, text: String?) {
        if (text.isNullOrBlank()) {
            Toast.makeText(context, "Nothing to copy", Toast.LENGTH_SHORT).show()
            return
        }
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("PayloadToolkit Log", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, context.getString(R.string.log_copied), Toast.LENGTH_SHORT).show()
    }
}
