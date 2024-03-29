package com.adindaef.ohquizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE
import com.adindaef.ohquizapp.model.Category
import com.adindaef.ohquizapp.model.Question

class QuizDBHelper(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object{
        private val DATABASE_NAME = "quiz.db"
        private val DATABASE_VER = 1

        // table
        val TABLE_NAME = "quiz_questions"

        // table column
        val COLUMN_ID = "id"
        val COLUMN_QUESTION = "question"
        val COLUMN_OPTION1 = "option1"
        val COLUMN_OPTION2 = "option2"
        val COLUMN_OPTION3 = "option3"
        val COLUMN_ANSWER = "answer"
        val COLUMN_DIFFICULTY = "difficulty"
        val COLUMN_CATEGORY = "category_id"

        // table category
        val TABLE_CATEGORY = "quiz_category"
        val COLUMN_ID_CATEGORY = "id_category"
        val COLUMN_NAME_CATEGORY = "name_category"

        private var instance: QuizDBHelper? = null

        @Synchronized
        fun getInstance(context: Context): QuizDBHelper{
            if (instance == null){
                instance = QuizDBHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY: String = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_QUESTION TEXT," +
                "$COLUMN_OPTION1 TEXT," +
                "$COLUMN_OPTION2 TEXT," +
                "$COLUMN_OPTION3 TEXT," +
                "$COLUMN_ANSWER INTEGER," +
                "$COLUMN_DIFFICULTY TEXT," +
                "$COLUMN_CATEGORY TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)

        val CREATE_TABLE_CATEGORY: String = ("CREATE TABLE $TABLE_CATEGORY (" +
                "$COLUMN_ID_CATEGORY INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME_CATEGORY TEXT)")
        db.execSQL(CREATE_TABLE_CATEGORY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        onCreate(db)
    }

    fun addQuestion(question: Question){
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COLUMN_QUESTION, question.question)
        cv.put(COLUMN_OPTION1, question.option1)
        cv.put(COLUMN_OPTION2, question.option2)
        cv.put(COLUMN_OPTION3, question.option3)
        cv.put(COLUMN_ANSWER, question.answer)
        cv.put(COLUMN_DIFFICULTY, question.difficulty)
        cv.put(COLUMN_CATEGORY, question.categoryID)
        db.insert(TABLE_NAME, null, cv)
        db.close()
    }

    fun addCategory(category: Category){
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COLUMN_NAME_CATEGORY, category.name)
        db.insert(TABLE_CATEGORY, null, cv)
        db.close()
    }

    val getAllQuestion: ArrayList<Question>
    get(){
        val listquestion = ArrayList<Question>()
        val selectquestion = "SELECT * FROM $TABLE_NAME"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectquestion, null)
        if (cursor.moveToFirst()){
            do {
                val question = Question()
                question.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                question.question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION))
                question.option1 = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1))
                question.option2 = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2))
                question.option3 = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3))
                question.answer = cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER))
                question.difficulty = cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY))
                question.categoryID = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))

                listquestion.add(question)
            } while (cursor.moveToNext())
        }
        db.close()
        return listquestion
    }

    fun getQuestion(categoryID: String?, difficulty: String?): ArrayList<Question>{
        val questionList = ArrayList<Question>()

        val db = this.writableDatabase

        val selection = COLUMN_CATEGORY + " = ? " +
                " AND " + COLUMN_DIFFICULTY + " = ? "
        val selectionArgs = arrayOf(categoryID.toString(), difficulty)

        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs, null, null, null
        )
        if (cursor.moveToFirst()){
            do {
                val question = Question()
                question.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                question.question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION))
                question.option1 = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1))
                question.option2 = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2))
                question.option3 = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3))
                question.answer = cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER))
                question.difficulty = cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY))
                question.categoryID = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))

                questionList.add(question)
            } while (cursor.moveToNext())
        }
        db.close()
        return questionList
    }

    val getAllCategory: ArrayList<Category>
    get() {
        val listCategory = ArrayList<Category>()
        val selectQueryCategory = "SELECT * FROM $TABLE_CATEGORY"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQueryCategory, null)

        if (cursor.moveToFirst()){
            do {
                val category = Category()
                category.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CATEGORY))
                category.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORY))

                listCategory.add(category)
            } while (cursor.moveToNext())
        }
        db.close()
        return listCategory
    }
}