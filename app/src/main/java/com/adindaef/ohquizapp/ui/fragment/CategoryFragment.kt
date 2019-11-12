package com.adindaef.ohquizapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adindaef.ohquizapp.model.CategoryData
import com.adindaef.ohquizapp.model.CategoryModel
import com.adindaef.ohquizapp.CustomAdapter
import com.adindaef.ohquizapp.R


class CategoryFragment : Fragment() {

    private lateinit var rvCategory: RecyclerView
    private var list: ArrayList<CategoryModel> = arrayListOf()
    private lateinit var myvalue:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.category_fragment, container, false)

        rvCategory = view.findViewById(R.id.recyclerView)
        rvCategory.setHasFixedSize(true)

        val bundle = this.arguments
        myvalue = bundle!!.getString("message")!!

        list.addAll(CategoryData(myvalue).listData)
        showRecyclerList()

        return view
}
    private fun showRecyclerList() {
        rvCategory.layoutManager = GridLayoutManager(context, 1)
        val listHeroAdapter = CustomAdapter(context, list, myvalue)
        rvCategory.adapter = listHeroAdapter
    }
}
