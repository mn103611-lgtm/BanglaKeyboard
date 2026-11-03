package com.example.banglakeyboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (16 * resources.displayMetrics.density).toInt()

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad, pad, pad)
        }

        val info = TextView(this).apply {
            text = "বাংলা কীবোর্ড চালু করার ধাপ:\n\n" +
                "১. 'কীবোর্ড চালু করুন' বোতামে চাপ দিয়ে 'বাংলা কীবোর্ড' অন করুন।\n" +
                "২. এরপর 'কীবোর্ড বেছে নিন' বোতামে চাপ দিয়ে বাংলা কীবোর্ড সিলেক্ট করুন।\n" +
                "৩. যেকোনো অ্যাপে টাইপ করার সময় কীবোর্ড পরিবর্তন করে বাংলা কীবোর্ড নিন।"
            textSize = 16f
        }
        layout.addView(info)

        val enableBtn = Button(this).apply {
            text = "কীবোর্ড চালু করুন"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            }
        }
        layout.addView(enableBtn)

        val pickBtn = Button(this).apply {
            text = "কীবোর্ড বেছে নিন"
            setOnClickListener {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showInputMethodPicker()
            }
        }
        layout.addView(pickBtn)

        setContentView(layout)
    }
}
