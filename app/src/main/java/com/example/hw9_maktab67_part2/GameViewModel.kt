package com.example.hw9_maktab67_part2

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var isCheated = false
    var answerButtonState = true
    var nextBtnIsEnable = true
    var prevBtnIsEnable = false
    var currentQuestionNumber = 0
    var listOfQuestion = listOf(
        "Argentina capital is: Buenos Aires",
        "Canada capital is: Yaounde",
        "China capital is: Beijing",
        "Egypt capital is: Dili",
        "France capital is: Paris",
        "Georgia capital is: Banjul",
        "Germany capital is: Berlin",
        "Italy capital is: Kingston",
        "Russia capital is: Moscow",
        "Somalia capital is: Honiara"
    )
    val questionAnswer = intArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1, 0)
    var userAnswer = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    var userCheated = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)

//    val contract = object : ActivityResultContract<String, Int>() {
//        override fun createIntent(context: Context, input: String?): Intent {
//            return Intent(MainActivity(), CheatActivity::class.java)
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Int {
//            return intent?.getIntExtra("cheatBoolean", 0).toString().toInt()
//        }
//    }
//    val callback = ActivityResultCallback<Int> { result ->
//        userCheated[currentQuestionNumber] = result
//        if (result == 1) MainActivity().cheatToast()
//    }
//    val activityResult = registerForActivityResult(contract, callback)

//    private fun cheat() {
//        val sp = getSharedPreferences("questionAnswer", MODE_PRIVATE)
//        val spEdit = sp.edit()
//        spEdit.putInt("answer", viewModel.questionAnswer[viewModel.currentQuestionNumber])
//        spEdit.apply()
//        viewModel.activityResult.launch("cheat")
//    }

    fun nextBtn(){
        if (currentQuestionNumber < listOfQuestion.size - 1) {
            currentQuestionNumber += 1
        }
        nextBtnIsEnable = currentQuestionNumber == listOfQuestion.size - 1
    }

    fun prevBtn(){
        if (currentQuestionNumber > 0) {
            currentQuestionNumber -= 1
        }
        prevBtnIsEnable = currentQuestionNumber == 0
    }

    fun isCheated(){
        isCheated = (userCheated[currentQuestionNumber] == 1 &&
            userAnswer[currentQuestionNumber] == questionAnswer[currentQuestionNumber])
    }

    fun answerButtonState(){
        answerButtonState = userAnswer[currentQuestionNumber] == -1
    }
}