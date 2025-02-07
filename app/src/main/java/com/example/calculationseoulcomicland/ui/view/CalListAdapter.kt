package com.example.calculationseoulcomicland.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.StockItem

class CalListAdapter (private val context: Context) :
    RecyclerView.Adapter<CalListAdapter.CalViewHolder>() {

    private var LOG_TAG = "CalListAdapter."
    private var itemClickListener : OnItemClickListener? = null

    private val differCallback = object : DiffUtil.ItemCallback<StockItem>() {
        override fun areItemsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalListAdapter.CalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cal_list_item_view, parent, false)
        return CalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: CalListAdapter.CalViewHolder,
        position: Int
    ) {
        Log.d(LOG_TAG, "onBindViewHolder: ")
        val user = differ.currentList[position]
        holder.bind(user)
    }

    inner class CalViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtTitle: TextView = itemView.findViewById(R.id.cal_item_name)
        private val txtCount: TextView = itemView.findViewById(R.id.cal_item_count)
        private val txtPrice: TextView = itemView.findViewById(R.id.cal_item_price)
        private val btnCountPlus: Button = itemView.findViewById(R.id.cal_item_count_plus)
        private val btnCountMinus: Button = itemView.findViewById(R.id.cal_item_count_minus)
        private val imgStockItem: ImageView = itemView.findViewById(R.id.cal_item_img)

        fun bind(item: StockItem){
            txtTitle.setText(item.title)
            txtPrice.setText(item.price.toString())

            val pos = adapterPosition
            if(item.img != null){
                imgStockItem.setImageURI(item.img)
                imgStockItem.setOnClickListener{
                        v -> itemClickListener?.onClick(v, pos)
                }
            }

            btnCountPlus.setOnClickListener{
                    v ->
                var count = txtCount.text.toString().toInt()
                count += 1
                txtCount.text = count.toString()
                itemClickListener?.onClick(v, pos, txtCount.text.toString().toInt())
            }
            btnCountMinus.setOnClickListener{
                    v ->
                var count = txtCount.text.toString().toInt()
                if (count <= 0){
                    count -= 1
                    txtCount.text = count.toString()
                    itemClickListener?.onClick(v, pos, txtCount.text.toString().toInt())
                }
            }
        }
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(v: View, pos: Int)
        fun onClick(v: View, pos: Int, count: Int)
    }
}