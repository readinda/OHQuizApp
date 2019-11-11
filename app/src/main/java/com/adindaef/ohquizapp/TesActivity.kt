package com.adindaef.ohquizapp

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.RadioButton
import android.widget.Toast
import com.adindaef.ohquizapp.model.Question
import kotlinx.android.synthetic.main.activity_tes.*
import java.util.*
import kotlin.collections.ArrayList

class TesActivity : AppCompatActivity() {

    companion object{
        var EXTRA_SCORE = "extraScore"
        val COUNTDOWN: Long = 30000

        val KEY_SCORE = "keyScore"
        val KEY_QUESTION_COUNT = "keyQuestionCount"
        val KEY_QUESTION_LIST = "keyQuestionList"
        val KEY_TIME_LEFT = "keyTimeLeft"
        val KEY_ANSWERED = "keyAnswered"
    }

    lateinit var db: QuizDBHelper
    var questionList: ArrayList<Question> = ArrayList<Question>()

    // menyimpan warna default dari text
    var txtColorDefaultRb: ColorStateList? = null
    var txtColorDefaultCd: ColorStateList? = null

    var countDownTimer: CountDownTimer? = null
    var timeLeft: Long = 0

    var questionCounter: Int = 0
    var questionCountTotal: Int = 0
    var currentQuestion: Question? = null

    var score: Int = 0
    var answered: Boolean = false

    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tes)

        db = QuizDBHelper(this)
        fillQuestionsTable()

        txtColorDefaultCd = txtCountDown.textColors
        txtColorDefaultRb = rb1.textColors

        val intent = intent
        val namaCategory = intent.getStringExtra("nama")
        val kelas = intent.getStringExtra("kelas")

        Toast.makeText(this,"" + namaCategory + kelas, Toast.LENGTH_SHORT).show()

        if (savedInstanceState == null){
            db = QuizDBHelper(this)
            questionList = db.getQuestion(kelas)

            questionCountTotal = questionList.size
            Collections.shuffle(questionList)
            showNextQuestion()
        } else {
            questionList = savedInstanceState.getParcelableArrayList(QuizActivity.KEY_QUESTION_LIST)!!
            questionCountTotal = questionList.size
            questionCounter = savedInstanceState.getInt(QuizActivity.KEY_QUESTION_COUNT)
            score = savedInstanceState.getInt(QuizActivity.KEY_SCORE)
            timeLeft = savedInstanceState.getLong(QuizActivity.KEY_TIME_LEFT)
            answered = savedInstanceState.getBoolean(QuizActivity.KEY_ANSWERED)
            currentQuestion = questionList.get(questionCounter - 1)

            if (!answered){
                startCountDown()
            } else {
                updateCountDownText()
                showSolution()
            }
        }

        btnConfirm.setOnClickListener {
            if (timeLeft > 0){
                if (!answered){
                    if (rb1.isChecked || rb2.isChecked || rb3.isChecked){
                        checkAnswer()
                    } else {
                        Toast.makeText(this, "Please select an answer!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    showNextQuestion()
                }
            } else {
                showNextQuestion()
            }
        }
    }

    private fun showNextQuestion() {
        rbGroup.clearCheck()
        rb1.setTextColor(txtColorDefaultRb)
        rb2.setTextColor(txtColorDefaultRb)
        rb3.setTextColor(txtColorDefaultRb)

        if (questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter)

            txtQuestion.setText(currentQuestion!!.question)
            rb1.setText(currentQuestion!!.option1)
            rb2.setText(currentQuestion!!.option2)
            rb3.setText(currentQuestion!!.option3)

            questionCounter++
            txtQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal)
            answered = false
            btnConfirm.setText("Confirm")

            timeLeft = QuizActivity.COUNTDOWN
            startCountDown()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val result = Intent()
        result.putExtra(QuizActivity.EXTRA_SCORE, score)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun startCountDown() {
        countDownTimer = object: CountDownTimer(timeLeft, 1000){
            override fun onFinish() {
                timeLeft = 0
                updateCountDownText()

                if (rb1.isChecked || rb2.isChecked || rb3.isChecked){
                    checkAnswer()
                } else {
                    showSolution()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateCountDownText()
            }
        }.start()
    }

    private fun updateCountDownText() {
        val minutes = (timeLeft / 1000) / 60
        val seconds = (timeLeft / 1000) % 60

        val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        txtCountDown.setText(timeFormatted)

        if (timeLeft < 10000){
            txtCountDown.setTextColor(Color.RED)
        } else {
            txtCountDown.setTextColor(txtColorDefaultCd)
        }
    }

    private fun checkAnswer() {
        answered = true
        countDownTimer!!.cancel()

        var rbSelected: RadioButton = findViewById(rbGroup.checkedRadioButtonId)
        val answer = rbGroup.indexOfChild(rbSelected) + 1

        if (answer == currentQuestion!!.answer){
            score++
            txtScore.setText("Score: $score")
        }
        showSolution()
    }

    private fun showSolution() {
        rb1.setTextColor(Color.RED)
        rb2.setTextColor(Color.RED)
        rb3.setTextColor(Color.RED)

        when (currentQuestion!!.answer){
            1 -> {
                rb1.setTextColor(Color.GREEN)
                txtQuestion.text = "Answer 1 is Correct"
            }
            2 -> {
                rb2.setTextColor(Color.GREEN)
                txtQuestion.text = "Answer 2 is Correct"
            }
            3 -> {
                rb3.setTextColor(Color.GREEN)
                txtQuestion.text = "Answer 3 is Correct"
            }
        }

        if (questionCounter < questionCountTotal){
            btnConfirm.text = "Next"
        } else {
            btnConfirm.text = "Finish"
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finishQuiz()
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_LONG).show()
        }

        backPressedTime = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null){
            countDownTimer!!.cancel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(QuizActivity.KEY_SCORE, score)
        outState.putInt(QuizActivity.KEY_QUESTION_COUNT, questionCounter)
        outState.putLong(QuizActivity.KEY_TIME_LEFT, timeLeft)
        outState.putBoolean(QuizActivity.KEY_ANSWERED, answered)
        outState.putParcelableArrayList(QuizActivity.KEY_QUESTION_LIST, questionList)
    }

    fun fillQuestionsTable() {
        val q1 = Question("A is correct", "A", "B", "C", 1, "First Class", 1)
        db.addQuestion(q1)
        val q2 = Question("B is correct", "A", "B", "C", 2, "Second Class", 1)
        db.addQuestion(q2)
        val q3 = Question("C is correct", "A", "B", "C", 3, "Third Class", 1 )
        db.addQuestion(q3)
        val q4 = Question("A is correct again", "A", "B", "C", 1, "First Class", 2)
        db.addQuestion(q4)
        val q5 = Question("B is correct again", "A", "B", "C", 2, "Second Class", 2)
        db.addQuestion(q5)
    }
}
