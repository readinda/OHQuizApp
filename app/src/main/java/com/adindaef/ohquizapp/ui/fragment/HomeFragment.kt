package com.adindaef.ohquizapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adindaef.ohquizapp.QuizDBHelper
import com.adindaef.ohquizapp.R


class HomeFragment : Fragment() {

    lateinit var db: QuizDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        db = QuizDBHelper(context)

        return root
    }
}