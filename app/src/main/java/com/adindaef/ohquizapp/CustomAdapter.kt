package com.adindaef.ohquizapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val c: Context?,
    private val listCategory: ArrayList<CategoryModel>,
    val myvalue: String
): RecyclerView.Adapter<CustomAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val category = listCategory[position]
        holder.imgPhoto.setImageResource(category.photo)
        holder.tvName.text = category.name

        holder.itemView.setOnClickListener {

            var intent = Intent((holder.itemView.context as Activity).baseContext, TesActivity::class.java)
            intent.putExtra("nama", category.name)
            intent.putExtra("kelas", myvalue)
            holder.itemView.context.startActivity(intent)

        }

    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.txtItem)
        var imgPhoto: ImageView = itemView.findViewById(R.id.imgitem)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoryModel)
    }
}