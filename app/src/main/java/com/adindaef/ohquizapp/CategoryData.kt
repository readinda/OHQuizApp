package com.adindaef.ohquizapp

import com.adindaef.ohquizapp.model.Category
import java.util.ArrayList

object CategoryData {
    var imageList = arrayOf(R.drawable.sience, R.drawable.math, R.drawable.bahasaindonesia)
    var textList = arrayOf(Category.IPA, Category.MATEMATIKA, Category.BAHASA_INDONESIA)

    val listData: ArrayList<CategoryModel>
        get() {
            val list = arrayListOf<CategoryModel>()
            for (position in textList.indices) {
                val albums = CategoryModel()
                albums.name = textList[position]
                albums.photo = imageList[position]
                list.add(albums)
            }
            return list
        }
}