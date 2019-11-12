package com.adindaef.ohquizapp

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.adindaef.ohquizapp.model.Category
import com.adindaef.ohquizapp.model.Question
import com.adindaef.ohquizapp.ui.category.CategoryFragment
import com.adindaef.ohquizapp.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*
import kotlin.collections.ArrayList
import android.R.id
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE



class QuizActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz)

        txtColorDefaultCd = txtCountDown.textColors
        txtColorDefaultRb = rb1.textColors

        val intent = intent
        val namaCategory = intent.getStringExtra("nama")
        val kelas = intent.getStringExtra("kelas")

        Toast.makeText(this,"" + namaCategory + kelas, Toast.LENGTH_SHORT).show()


        txtDifficulty.setText("Grade: " + kelas)
        txtCategory.setText("Category: " + namaCategory)

        db = QuizDBHelper(this)
        questionList = db.getAllQuestion
        if (questionList.size > 0){
            //categories

        }
        else{
            fillQuestion()
        }


        if (savedInstanceState == null){
            db = QuizDBHelper(this)
            questionList = db.getQuestion(namaCategory, kelas)

            questionCountTotal = questionList.size
            Collections.shuffle(questionList)
            showNextQuestion()
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST)!!
            questionCountTotal = questionList.size
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT)
            score = savedInstanceState.getInt(KEY_SCORE)
            timeLeft = savedInstanceState.getLong(KEY_TIME_LEFT)
            answered = savedInstanceState.getBoolean(KEY_ANSWERED)
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

            timeLeft = COUNTDOWN
            startCountDown()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
//        val result = Intent(this, HomeFragment::class.java)
//        result.putExtra(EXTRA_SCORE, score)
//        setResult(Activity.RESULT_OK, result)
//        finish()
//        val prefs = this.activity!!.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
//        val editor = prefs.edit()
//        editor.putInt(KEY_HIGHSCORE, highscore)
//        editor.apply()
//        val pref = getPreferences(Context.MODE_PRIVATE)
//        val edt = pref.edit()
//        edt.putInt(EXTRA_SCORE, score)
//        edt.apply()

        val result = Intent()
        result.putExtra(EXTRA_SCORE, score)
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
        outState.putInt(KEY_SCORE, score)
        outState.putInt(KEY_QUESTION_COUNT, questionCounter)
        outState.putLong(KEY_TIME_LEFT, timeLeft)
        outState.putBoolean(KEY_ANSWERED, answered)
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList)
    }

    private fun fillQuestion() {
        val q1m7 = Question(
            getString(R.string.math7_1),
            (getString(R.string.math71_a)),
            (getString(R.string.math71_b)),
            (getString(R.string.math71_c)),
            2,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.MATEMATIKA
        )

        val q2m7 = Question(
            getString(R.string.math7_2),
            (getString(R.string.math72_a)),
            (getString(R.string.math72_b)),
            (getString(R.string.math72_c)),
            1,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.MATEMATIKA

        )

        val q3m7 = Question(
            getString(R.string.math7_3),
            (getString(R.string.math73_a)),
            (getString(R.string.math73_b)),
            (getString(R.string.math73_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.MATEMATIKA

        )

        val q4m7 = Question(
            getString(R.string.math7_4),
            (getString(R.string.math74_a)),
            (getString(R.string.math74_b)),
            (getString(R.string.math74_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.MATEMATIKA

        )

        val q5m7 = Question(
            getString(R.string.math7_5),
            (getString(R.string.math75_a)),
            (getString(R.string.math75_b)),
            (getString(R.string.math75_c)),
            2,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.MATEMATIKA

        )

        val q1s7 = Question(
            getString(R.string.science7_1),
            (getString(R.string.science71_a)),
            (getString(R.string.science71_b)),
            (getString(R.string.science71_c)),
            1,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.IPA
        )

        val q2s7 = Question(
            getString(R.string.science7_2),
            (getString(R.string.science72_a)),
            (getString(R.string.science72_b)),
            (getString(R.string.science72_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.IPA
        )

        val q3s7 = Question(
            getString(R.string.science7_3),
            (getString(R.string.science73_a)),
            (getString(R.string.science73_b)),
            (getString(R.string.science73_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.IPA
        )

        val q4s7 = Question(
            getString(R.string.science7_4),
            (getString(R.string.science74_a)),
            (getString(R.string.science74_b)),
            (getString(R.string.science75_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.IPA
        )

        val q5s7 = Question(
            getString(R.string.science7_5),
            (getString(R.string.science75_a)),
            (getString(R.string.science75_b)),
            (getString(R.string.science75_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.IPA
        )

        val q1i7 = Question(
            getString(R.string.indonesian7_1),
            (getString(R.string.indonesian71_a)),
            (getString(R.string.indonesian71_b)),
            (getString(R.string.indonesian71_c)),
            2,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.BAHASA_INDONESIA
        )

        val q2i7 = Question(
            getString(R.string.indonesian7_2),
            (getString(R.string.indonesian72_a)),
            (getString(R.string.indonesian72_b)),
            (getString(R.string.indonesian72_c)),
            2,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.BAHASA_INDONESIA
        )

        val q3i7 = Question(
            getString(R.string.indonesian7_3),
            (getString(R.string.indonesian73_a)),
            (getString(R.string.indonesian73_b)),
            (getString(R.string.indonesian73_c)),
            2,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.BAHASA_INDONESIA
        )


        val q4i7 = Question(
            getString(R.string.indonesian7_4),
            (getString(R.string.indonesian74_a)),
            (getString(R.string.indonesian74_b)),
            (getString(R.string.indonesian74_c)),
            2,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.BAHASA_INDONESIA
        )

        val q5i7 = Question(
            getString(R.string.indonesian7_5),
            (getString(R.string.indonesian75_a)),
            (getString(R.string.indonesian75_b)),
            (getString(R.string.indonesian75_c)),
            3,

            Question.DIFFICULTY_FIRST_CLASS,
            Category.BAHASA_INDONESIA
        )

        val q1m8 = Question(
            getString(R.string.math8_1),
            (getString(R.string.math81_a)),
            (getString(R.string.math81_b)),
            (getString(R.string.math81_c)),
            2,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.MATEMATIKA
        )

        val q2m8 = Question(
            getString(R.string.math8_2),
            (getString(R.string.math82_a)),
            (getString(R.string.math82_b)),
            (getString(R.string.math82_c)),
            3,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.MATEMATIKA
        )

        val q3m8 = Question(
            getString(R.string.math8_3),
            (getString(R.string.math83_a)),
            (getString(R.string.math83_b)),
            (getString(R.string.math83_c)),
            2,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.MATEMATIKA
        )

        val q4m8 = Question(
            getString(R.string.math8_4),
            (getString(R.string.math84_a)),
            (getString(R.string.math84_b)),
            (getString(R.string.math84_c)),
            3,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.MATEMATIKA
        )

        val q5m8 = Question(
            getString(R.string.math8_5),
            (getString(R.string.math85_a)),
            (getString(R.string.math85_b)),
            (getString(R.string.math85_c)),
            3,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.MATEMATIKA
        )

        val q1s8 = Question(
            getString(R.string.science8_1),
            (getString(R.string.science81_a)),
            (getString(R.string.science81_b)),
            (getString(R.string.science81_c)),
            1,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.IPA
        )

        val q2s8 = Question(
            getString(R.string.science8_2),
            (getString(R.string.science82_a)),
            (getString(R.string.science82_b)),
            (getString(R.string.science82_c)),
            1,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.IPA
        )

        val q3s8 = Question(
            getString(R.string.science8_3),
            (getString(R.string.science83_a)),
            (getString(R.string.science83_b)),
            (getString(R.string.science83_c)),
            3,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.IPA
        )

        val q4s8 = Question(
            getString(R.string.science8_4),
            (getString(R.string.science84_a)),
            (getString(R.string.science84_b)),
            (getString(R.string.science84_c)),
            1,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.IPA
        )

        val q5s8 = Question(
            getString(R.string.science8_5),
            (getString(R.string.science85_a)),
            (getString(R.string.science85_b)),
            (getString(R.string.science85_c)),
            1,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.IPA
        )

        val q1i8 = Question(
            getString(R.string.indonesian8_1),
            (getString(R.string.indonesian81_a)),
            (getString(R.string.indonesian81_b)),
            (getString(R.string.indonesian81_c)),
            1,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q2i8 = Question(
            getString(R.string.indonesian8_2),
            (getString(R.string.indonesian81_a)),
            (getString(R.string.indonesian82_b)),
            (getString(R.string.indonesian82_c)),
            2,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q3i8 = Question(
            getString(R.string.indonesian8_3),
            (getString(R.string.indonesian83_a)),
            (getString(R.string.indonesian83_b)),
            (getString(R.string.indonesian83_c)),
            2,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q4i8 = Question(
            getString(R.string.indonesian8_4),
            (getString(R.string.indonesian84_a)),
            (getString(R.string.indonesian84_b)),
            (getString(R.string.indonesian84_c)),
            3,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q5i8 = Question(
            getString(R.string.indonesian8_5),
            (getString(R.string.indonesian85_a)),
            (getString(R.string.indonesian85_b)),
            (getString(R.string.indonesian85_c)),
            2,

            Question.DIFFICULTY_SECOND_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q1m9 = Question(
            getString(R.string.math9_1),
            (getString(R.string.math91_a)),
            (getString(R.string.math91_b)),
            (getString(R.string.math91_c)),
            1,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.MATEMATIKA
        )

        val q2m9 = Question(
            getString(R.string.math9_2),
            (getString(R.string.math92_a)),
            (getString(R.string.math92_b)),
            (getString(R.string.math92_c)),
            3,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.MATEMATIKA
        )

        val q3m9 = Question(
            getString(R.string.math9_3),
            (getString(R.string.math93_a)),
            (getString(R.string.math93_b)),
            (getString(R.string.math93_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.MATEMATIKA
        )

        val q4m9 = Question(
            getString(R.string.math9_4),
            (getString(R.string.math94_a)),
            (getString(R.string.math94_b)),
            (getString(R.string.math94_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.MATEMATIKA
        )

        val q5m9 = Question(
            getString(R.string.math9_5),
            (getString(R.string.math95_a)),
            (getString(R.string.math95_b)),
            (getString(R.string.math95_c)),
            3,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.MATEMATIKA
        )

        val q1s9 = Question(
            getString(R.string.science9_1),
            (getString(R.string.science91_a)),
            (getString(R.string.science91_b)),
            (getString(R.string.science91_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.IPA
        )

        val q2s9 = Question(
            getString(R.string.scienced9_2),
            (getString(R.string.science92_a)),
            (getString(R.string.science92_b)),
            (getString(R.string.science92_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.IPA
        )

        val q3s9 = Question(
            getString(R.string.science9_3),
            (getString(R.string.science93_a)),
            (getString(R.string.science93_b)),
            (getString(R.string.science93_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.IPA
        )

        val q4s9 = Question(
            getString(R.string.science9_4),
            (getString(R.string.science94_a)),
            (getString(R.string.science94_b)),
            (getString(R.string.science94_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.IPA
        )

        val q5s9 = Question(
            getString(R.string.science9_5),
            (getString(R.string.science95_a)),
            (getString(R.string.science95_b)),
            (getString(R.string.science95_c)),
            1,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.IPA
        )

        val q1i9 = Question(
            getString(R.string.indonesian9_1),
            (getString(R.string.indonesian91_a)),
            (getString(R.string.indonesian91_b)),
            (getString(R.string.indonesian91_c)),
            1,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q2i9 = Question(
            getString(R.string.indonesian9_2),
            (getString(R.string.indonesian92_a)),
            (getString(R.string.indonesian92_b)),
            (getString(R.string.indonesian92_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q3i9 = Question(
            getString(R.string.indonesian9_3),
            (getString(R.string.indonesian93_a)),
            (getString(R.string.indonesian93_b)),
            (getString(R.string.indonesian93_c)),
            2,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q4i9 = Question(
            getString(R.string.indonesian9_4),
            (getString(R.string.indonesian94_a)),
            (getString(R.string.indonesian94_b)),
            (getString(R.string.indonesian94_c)),
            3,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.BAHASA_INDONESIA
        )

        val q5i9 = Question(
            getString(R.string.indonesian9_5),
            (getString(R.string.indonesian95_a)),
            (getString(R.string.indonesian95_b)),
            (getString(R.string.indonesian95_c)),
            1,

            Question.DIFFICULTY_THIRD_GRADE,
            Category.BAHASA_INDONESIA
        )

        db.addQuestion(q1m7)
        db.addQuestion(q2m7)
        db.addQuestion(q3m7)
        db.addQuestion(q4m7)
        db.addQuestion(q5m7)

        db.addQuestion(q1s7)
        db.addQuestion(q2s7)
        db.addQuestion(q3s7)
        db.addQuestion(q4s7)
        db.addQuestion(q5s7)

        db.addQuestion(q1i7)
        db.addQuestion(q2i7)
        db.addQuestion(q3i7)
        db.addQuestion(q4i7)
        db.addQuestion(q5i7)

        db.addQuestion(q1m8)
        db.addQuestion(q2m8)
        db.addQuestion(q3m8)
        db.addQuestion(q4m8)
        db.addQuestion(q5m8)

        db.addQuestion(q1s8)
        db.addQuestion(q2s8)
        db.addQuestion(q3s8)
        db.addQuestion(q4s8)
        db.addQuestion(q5s8)

        db.addQuestion(q1i8)
        db.addQuestion(q2i8)
        db.addQuestion(q3i8)
        db.addQuestion(q4i8)
        db.addQuestion(q5i8)

        db.addQuestion(q1m9)
        db.addQuestion(q2m9)
        db.addQuestion(q3m9)
        db.addQuestion(q4m9)
        db.addQuestion(q5m9)

        db.addQuestion(q1s9)
        db.addQuestion(q2s9)
        db.addQuestion(q3s9)
        db.addQuestion(q4s9)
        db.addQuestion(q5s9)

        db.addQuestion(q1i9)
        db.addQuestion(q2i9)
        db.addQuestion(q3i9)
        db.addQuestion(q4i9)
        db.addQuestion(q5i9)

    }
}
