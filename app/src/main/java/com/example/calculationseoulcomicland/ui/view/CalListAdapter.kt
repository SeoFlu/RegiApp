package com.example.calculationseoulcomicland.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.StockItem

class CalListAdapter (val itemList: ArrayList<StockItem>) :
    RecyclerView.Adapter<CalListAdapter.CalViewHolder>() {

        private var itemArrList : ArrayList<StockItem> = ArrayList<StockItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalListAdapter.CalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cal_list_item_view, parent, false)
        itemArrList.addAll(itemList)
        return CalViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalListAdapter.CalViewHolder, position: Int) {
        if(itemArrList.isNotEmpty()) {
            holder.tv_title.setText(itemArrList[position].title)
            holder.tv_count.setText("0")
            holder.tv_price.setText(itemArrList[position].price.toString())
        }
    }

    override fun getItemCount(): Int {
        return itemArrList.count()
    }

    fun updateList(itemList: ArrayList<StockItem>) {
        itemArrList.clear()
        itemArrList.addAll(itemList)
        notifyDataSetChanged()
    }

    fun removeLastedItem() {
        if(itemArrList.isNotEmpty()){
            itemArrList.removeAt(itemArrList.size - 1)
        }
        notifyDataSetChanged()
    }

    fun getListData():String {
        var arrayListStr : String = ""
        for(i : Int in 0.. itemArrList.size - 1 ){
            arrayListStr += (itemArrList[i].title + "_")
            arrayListStr += (itemArrList[i].count.toString() + "_")
            arrayListStr += (itemArrList[i].price.toString() + "_")
            if(i != itemArrList.size - 1){
                arrayListStr += "&"
            }
        }
        return arrayListStr
    }

    inner class CalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_title = itemView.findViewById<TextView>(R.id.item_title)
        val tv_count = itemView.findViewById<EditText>(R.id.item_count)
        val tv_price = itemView.findViewById<TextView>(R.id.item_price)
    }
}