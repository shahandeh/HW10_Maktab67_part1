package com.example.hw9_maktab67_part2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class CheatActivity : AppCompatActivity() {
    var cheated = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        findViewById<MaterialButton>(R.id.showAnswer).setOnClickListener {
            val sp = getSharedPreferences("questionAnswer", Context.MODE_PRIVATE)
            findViewById<TextView>(R.id.tv).text =
                if (sp.getInt("answer", 0) == 0) "false"
                else "true"
            cheated = 1
            intent.putExtra("cheatBoolean", 1)
            setResult(RESULT_OK, intent)
        }
    }

    override fun onBackPressed() {
        if (cheated == 0){
            intent.putExtra("cheatBoolean", 0)
            setResult(RESULT_OK, intent)
        }
        super.onBackPressed()
    }
}