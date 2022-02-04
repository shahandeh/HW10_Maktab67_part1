package com.example.hw9_maktab67_part2

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.hw9_maktab67_part2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels()

    lateinit var questionTV: TextView



    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        questionTV = bind.tv

        questionTV.text = viewModel.listOfQuestion[viewModel.currentQuestionNumber]

        bind.prev.isEnabled = viewModel.prevBtnIsEnable

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

        bind.cheat.setOnClickListener {
            startActivity(Intent(this, CheatActivity::class.java))
        }
    }



    private fun setCurrentQuestionNumber(v: View) {
        if (v.id == bind.next.id) {
            viewModel.nextBtn()
            bind.prev.isEnabled = true
        } else {
            viewModel.prevBtn()
            bind.next.isEnabled = true
        }
        questionTV.text = viewModel.listOfQuestion[viewModel.currentQuestionNumber]
        bind.next.isEnabled = viewModel.nextBtnIsEnable
        bind.prev.isEnabled = viewModel.prevBtnIsEnable
    }

    fun answerButtonColor() {
        if (viewModel.userAnswer[viewModel.currentQuestionNumber] == -1) {
            bind.answerTrue.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.normal))
            bind.answerFalse.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.normal))
        } else if (viewModel.userAnswer[viewModel.currentQuestionNumber] == viewModel.questionAnswer[viewModel.currentQuestionNumber]) {
            if (viewModel.userAnswer[viewModel.currentQuestionNumber] == 1) {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.green))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.normal))
            } else {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.normal))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.green))
            }
        } else {
            if (viewModel.userAnswer[viewModel.currentQuestionNumber] == 1) {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.red))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.normal))
            } else {
                bind.answerTrue.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.normal))
                bind.answerFalse.setBackgroundColor(ContextCompat.getColor(MainActivity(), R.color.red))
            }
        }
    }

    fun cheatToast() {
        viewModel.isCheated()
        if (viewModel.isCheated)
            Toast.makeText(this, "Cheating is Wrong", Toast.LENGTH_SHORT).show()
    }

    private fun answerButtonEnable() {
        viewModel.answerButtonState()
        bind.answerTrue.isEnabled = viewModel.answerButtonState
        bind.answerFalse.isEnabled = viewModel.answerButtonState
    }

    private fun answerValidateToast(int: Int) {
        viewModel.userAnswer[viewModel.currentQuestionNumber] = int
        if (viewModel.questionAnswer[viewModel.currentQuestionNumber] == int)
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = bundleOf(
            "userAnswer" to viewModel.userAnswer,
            "userCheated" to viewModel.userCheated,
            "currentQuestionNumber" to viewModel.currentQuestionNumber
        )
        outState.putBundle("save", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val bundle = savedInstanceState.getBundle("save")
        if (bundle != null) {
            viewModel.userAnswer = bundle.getIntArray("userAnswer")!!
            viewModel.userCheated = bundle.getIntArray("userCheated")!!
            viewModel.currentQuestionNumber = bundle.getInt("currentQuestionNumber")
        }

        answerButtonColor()
        cheatToast()
        viewModel.currentQuestionNumber--
        setCurrentQuestionNumber(bind.next)
    }
}
