package com.adindaef.ohquizapp.model

import com.adindaef.ohquizapp.R
import java.util.ArrayList

class CategoryData(private val kelas: String) {
    var imageList1 = arrayOf(
        R.drawable.microscope,
        R.drawable.blackboard,
        R.drawable.indonesiaflag
    )
    var imageList2 = arrayOf(
        R.drawable.biology,
        R.drawable.geometry,
        R.drawable.indonesiaflag
    )
    var imageList3 = arrayOf(
        R.drawable.chemistry,
        R.drawable.abacus,
        R.drawable.indonesiaflag
    )
    var textList = arrayOf(Category.IPA, Category.MATEMATIKA, Category.BAHASA_INDONESIA)

    companion object

    val listData: ArrayList<CategoryModel>
        get() {
            val list = arrayListOf<CategoryModel>()
            when (kelas) {
                Question.DIFFICULTY_FIRST_CLASS -> {
                    for (position in textList.indices) {
                        val albums = CategoryModel()
                        albums.name = textList[position]
                        albums.photo = imageList1[position]
                        list.add(albums)
                    }
                }
                Question.DIFFICULTY_SECOND_GRADE -> {
                    for (position in textList.indices) {
                        val albums = CategoryModel()
                        albums.name = textList[position]
                        albums.photo = imageList2[position]
                        list.add(albums)
                    }
                }
                Question.DIFFICULTY_THIRD_GRADE -> {
                    for (position in textList.indices) {
                        val albums = CategoryModel()
                        albums.name = textList[position]
                        albums.photo = imageList3[position]
                        list.add(albums)
                    }
                }
            }

            return list
        }
}