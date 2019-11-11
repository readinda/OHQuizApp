package com.adindaef.ohquizapp.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adindaef.ohquizapp.QuizActivity
import com.adindaef.ohquizapp.QuizDBHelper
import com.adindaef.ohquizapp.R
import com.adindaef.ohquizapp.model.Category
import com.adindaef.ohquizapp.model.Question
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    companion object {
        var REQUEST_CODE_QUIZ = 1
        val EXTRA_DIFFICULTY = "extraDifficulty"

        var SHARED_PREF = "sharePref"
        var KEY_HIGHSCORE = "keyHighscore"

        var highscore: Int = 0
    }

    lateinit var db: QuizDBHelper
    var questionList: ArrayList<Question> = ArrayList()

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)



        db = QuizDBHelper(context)


//        loadHighscore()
//        loadSpinnerDifficulty()

//        root.btnStartQuiz.setOnClickListener {
//            questionList = db.getAllQuestion
//            if (questionList.size > 0) {
//                val difficulty = spinnerDifficulty.selectedItem.toString()
//                val i = Intent(context, QuizActivity::class.java)
//                i.putExtra(EXTRA_DIFFICULTY, difficulty)
//                startActivityForResult(i, REQUEST_CODE_QUIZ)
//            } else {
//                fillQuestion()
//            }
//        }

        return root
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

        db.addQuestion(q1i9)
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

    private fun loadHighscore() {
        val prefs = this.activity!!.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        highscore = prefs.getInt(KEY_HIGHSCORE, 0)
        txtHighScore.setText("Highscore: $highscore")
    }

//    private fun loadSpinnerDifficulty() {
//        val difficultyLevels = Question.getAllDifficultyLevels()
//        val adapterDifficulty = ArrayAdapter(, R.layout.support_simple_spinner_dropdown_item, difficultyLevels)
//        spinnerDifficulty.adapter = adapterDifficulty
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == Activity.RESULT_OK) {
                val score = data!!.getIntExtra(QuizActivity.KEY_SCORE, 0)
                if (score > highscore) {
                    updateScore(score)
                }
            }
        }
    }

    private fun updateScore(score: Int) {
        highscore = score
        txtHighScore.setText("Highscore: $highscore")

        val prefs = this.activity!!.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(KEY_HIGHSCORE, highscore)
        editor.apply()
    }
}