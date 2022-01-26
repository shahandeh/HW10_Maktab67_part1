package com.example.hw9_maktab67_part2

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat
import com.example.hw9_maktab67_part2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var currentQuestionNumber = 0
    lateinit var questionTV: TextView
    private var listOfQuestion = listOf(
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
    private val questionAnswer = intArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1, 0)
    private var userAnswer = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    private var userCheated = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

//        if (savedInstanceState != null) {
//            userAnswer = savedInstanceState.getIntArray("userAnswer")!!
//            userCheated = savedInstanceState.getIntArray("userCheated")!!
//        }

        questionTV = bind.tv

        questionTV.text = listOfQuestion[currentQuestionNumber]

        bind.prev.isEnabled = false

        bind.answerTrue.setOnClickListener {
            answerValidateToast(1)
            answerButtonEnable()
            answerButtonColor()
        }
        bind.answerFalse.setOnClickListener {
            answerValidateToast(0)
            answerButtonEnable()
            answerButtonColor()
        }

        bind.next.setOnClickListener {
            setCurrentQuestionNumber(it)
            answerButtonEnable()
            answerButtonColor()
            cheatToast()
        }

        bind.prev.setOnClickListener {
            setCurrentQuestionNumber(it)
            answerButtonEnable()
            answerButtonColor()
            cheatToast()
        }

        val contract = object : ActivityResultContract<String, Int>() {
            override fun createIntent(context: Context, input: String?): Intent {
                return Intent(this@MainActivity, CheatActivity::class.java)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Int {
                return intent?.getIntExtra("cheatBoolean", 0).toString().toInt()
            }
        }
        val callback = ActivityResultCallback<Int> { result ->
            userCheated[currentQuestionNumber] = result
            if (result == 1) Toast.makeText(this, "Cheating is Wrong", Toast.LENGTH_SHORT).show()
        }
        val activityResult = registerForActivityResult(contract, callback)
        bind.cheat.setOnClickListener { activityResult.launch("cheat") }
    }

    private fun setCurrentQuestionNumber(v: View) {
        if (v.id == bind.next.id) {
            if (currentQuestionNumber < listOfQuestion.size - 1) {
                currentQuestionNumber += 1
                questionTV.text = listOfQuestion[currentQuestionNumber].toString()
            }
            bind.prev.isEnabled = true
        } else {
            if (currentQuestionNumber > 0) {
                currentQuestionNumber -= 1
                questionTV.text = listOfQuestion[currentQuestionNumber].toString()
            }
            bind.next.isEnabled = true
        }

        if (currentQuestionNumber == listOfQuestion.size - 1) bind.next.isEnabled = false
        if (currentQuestionNumber == 0) bind.prev.isEnabled = false
    }

    private fun cheatToast() {
        if (userCheated[currentQuestionNumber] == 1 &&
            userAnswer[currentQuestionNumber] == questionAnswer[currentQuestionNumber]
        )
            Toast.makeText(this, "Cheating is Wrong", Toast.LENGTH_SHORT).show()
    }

    private fun answerButtonEnable() {
        val boolean = userAnswer[currentQuestionNumber] == -1
        bind.answerTrue.isEnabled = boolean
        bind.answerFalse.isEnabled = boolean
    }

    private fun answerValidateToast(int: Int) {
        userAnswer[currentQuestionNumber] = int
        if (questionAnswer[currentQuestionNumber] == int)
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
    }

    private fun answerButtonColor() {
        if (userAnswer[currentQuestionNumber] == -1) {
            bind.answerTrue.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            bind.answerFalse.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
        } else if (userAnswer[currentQuestionNumber] == questionAnswer[currentQuestionNumber]) {
            if (userAnswer[currentQuestionNumber] == 1) {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(this, R.color.normal))
            } else {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(this, R.color.normal))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            }
        } else {
            if (userAnswer[currentQuestionNumber] == 1) {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(this, R.color.normal))
            } else {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(this, R.color.normal))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putIntArray("userAnswer", userAnswer)
        outState.putIntArray("userCheated", userCheated)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        var bundle = Bundle()
        userAnswer = bundle.getIntArray("userAnswer") as IntArray
        userCheated = bundle.getIntArray("userCheated") as IntArray
    }

}