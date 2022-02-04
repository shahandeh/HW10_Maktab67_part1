package com.example.hw9_maktab67_part2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.button.MaterialButton

class CheatActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        findViewById<MaterialButton>(R.id.showAnswer).setOnClickListener {
            findViewById<TextView>(R.id.tv).text = viewModel.questionAnswer[viewModel.currentQuestionNumber].toString()
            viewModel.userCheated[viewModel.currentQuestionNumber] = 1
        }
    }
}