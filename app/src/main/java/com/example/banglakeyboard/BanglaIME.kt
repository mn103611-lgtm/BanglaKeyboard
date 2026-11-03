package com.example.banglakeyboard

import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class BanglaIME : InputMethodService() {

    private val buffer = mutableListOf<String>()

    private val rows = listOf(
        listOf("অ", "আ", "ই", "ঈ", "উ", "ঊ", "ঋ", "এ", "ঐ"),
        listOf("ও", "ঔ", "ং", "ঃ", "ঁ", "ক", "খ", "গ", "ঘ"),
        listOf("ঙ", "চ", "ছ", "জ", "ঝ", "ঞ", "ট", "ঠ", "ড"),
        listOf("ঢ", "ণ", "ত", "থ", "দ", "ধ", "ন", "প", "ফ"),
        listOf("ব", "ভ", "ম", "য", "র", "ল", "শ", "ষ", "স"),
        listOf("হ", "\u09DC", "\u09DD", "\u09DF", "\u09CE", "া", "ি", "ী", "ু"),
        listOf("ূ", "ৃ", "ে", "ৈ", "ো", "ৌ", "\u09CD", "DEL"),
        listOf("।", "?", "SPACE", "ENTER")
    )

    override fun onCreateInputView(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#E9EBF2"))
            setPadding(dp(3), dp(4), dp(3), dp(4))
        }
        for (row in rows) {
            root.addView(buildRow(row))
        }
        return root
    }

    private fun buildRow(keys: List<String>): LinearLayout {
        val line = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        for (k in keys) {
            val btn = Button(this).apply {
                text = labelOf(k)
                setTextSize(16f)
                setAllCaps(false)
                setTextColor(Color.parseColor("#1D1F27"))
                setBackgroundColor(Color.WHITE)
                setPadding(0, 0, 0, 0)
                setOnClickListener { onKey(k) }
            }
            val weight = if (k == "SPACE") 4f else 1f
            val params = LinearLayout.LayoutParams(0, dp(44), weight)
            params.setMargins(dp(2), dp(2), dp(2), dp(2))
            line.addView(btn, params)
        }
        return line
    }

    private fun labelOf(k: String): String = when (k) {
        "DEL" -> "⌫"
        "ENTER" -> "↵"
        "SPACE" -> "স্পেস"
        "\u09CD" -> "হসন্ত"
        else -> k
    }

    private fun onKey(k: String) {
        when (k) {
            "DEL" -> onDelete()
            "ENTER" -> {
                commitBuffer()
                sendDownUpKeyEvent(KeyEvent.KEYCODE_ENTER)
            }
            "SPACE" -> {
                commitBuffer()
                currentInputConnection?.commitText(" ", 1)
            }
            "।", "?" -> {
                commitBuffer()
                currentInputConnection?.commitText(k, 1)
            }
            else -> {
                buffer.add(k)
                updateComposing()
            }
        }
    }

    private fun onDelete() {
        if (buffer.isEmpty()) {
            sendDownUpKeyEvent(KeyEvent.KEYCODE_DEL)
            return
        }
        buffer.removeAt(buffer.size - 1)
        if (buffer.isEmpty()) {
            currentInputConnection?.setComposingText("", 1)
            currentInputConnection?.finishComposingText()
        } else {
            updateComposing()
        }
    }

    private fun updateComposing() {
        currentInputConnection?.setComposingText(RomanConverter.convert(buffer), 1)
    }

    private fun commitBuffer() {
        if (buffer.isNotEmpty()) {
            currentInputConnection?.finishComposingText()
            buffer.clear()
        }
    }

    override fun onFinishInput() {
        commitBuffer()
        super.onFinishInput()
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()
}
