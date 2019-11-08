package com.adindaef.ohquizapp.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adindaef.ohquizapp.QuizActivity
import com.adindaef.ohquizapp.QuizDBHelper
import com.adindaef.ohquizapp.R
import com.adindaef.ohquizapp.model.Category
import com.adindaef.ohquizapp.model.Question
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    companion object{
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
//            if (questionList.size > 0){
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
        val q1 = Question(
            "2 + 3 =",
            "5",
            "6",
            "7",
            1,
            Question.DIFFICULTY_FIRST_CLASS,
            Category.MATEMATIKA
        )
        db.addQuestion(q1)
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

        if (requestCode == REQUEST_CODE_QUIZ){
            if (resultCode == Activity.RESULT_OK){
                val score = data!!.getIntExtra(QuizActivity.KEY_SCORE, 0)
                if (score > highscore){
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