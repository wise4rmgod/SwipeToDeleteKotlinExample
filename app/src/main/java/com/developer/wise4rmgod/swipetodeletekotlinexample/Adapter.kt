package com.developer.wise4rmgod.swipetodeletekotlinexample

import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private var dataList: ArrayList<Model>,
    private val context: Context
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_item,
                parent,
                false
            )
        )

    }


    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataList.size)
    }

    fun restoreItem(model: Model, position: Int) {
        dataList.add(position, model)
        // notify item added by position
        notifyItemInserted(position)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val dataModel = dataList.get(position)
        holder.fullname.text = dataModel.name

    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var fullname: TextView = itemLayoutView.findViewById(R.id.tv)

    }

}