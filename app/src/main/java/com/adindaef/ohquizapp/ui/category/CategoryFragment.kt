package com.adindaef.ohquizapp.ui.category

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adindaef.ohquizapp.CategoryData
import com.adindaef.ohquizapp.CategoryModel
import com.adindaef.ohquizapp.CustomAdapter
import com.adindaef.ohquizapp.R
import kotlinx.android.synthetic.main.category_fragment.*

class CategoryFragment : Fragment() {

    private lateinit var rvCategory: RecyclerView
    private var list: ArrayList<CategoryModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.category_fragment, container, false)

        rvCategory = view.findViewById(R.id.recyclerView)
        rvCategory.setHasFixedSize(true)

        list.addAll(CategoryData.listData)
        showRecyclerList()

        return view
}
    private fun showRecyclerList() {
        rvCategory.layoutManager = GridLayoutManager(context, 1)
        val listHeroAdapter = CustomAdapter(list)
        rvCategory.adapter = listHeroAdapter
    }
}
