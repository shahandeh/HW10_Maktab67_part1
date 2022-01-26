package com.example.hw9_maktab67_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class CheatActivity : AppCompatActivity() {
    var cheated = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        findViewById<MaterialButton>(R.id.showAnswer).setOnClickListener {
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