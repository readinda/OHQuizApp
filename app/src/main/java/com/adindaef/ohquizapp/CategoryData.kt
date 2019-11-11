package com.adindaef.ohquizapp

import java.util.ArrayList

object CategoryData {
    var imageList = arrayOf(R.drawable.sience, R.drawable.math, R.drawable.bahasaindonesia)
    var textList = arrayOf("IPA", "Matematika", "Bahasa Indonesa")

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