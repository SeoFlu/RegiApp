package com.example.calculationseoulcomicland.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.StockItem

class StockListAdapter (private val context: Context) :
    RecyclerView.Adapter<StockListAdapter.StockViewHolder>() {

    private var LOG_TAG = "StockListAdapter."

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
    ): StockViewHolder {
        Log.d(LOG_TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(context).inflate(R.layout.stock_list_item_view, parent, false)
        return StockViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        Log.d(LOG_TAG, "onBindViewHolder: ")
        val user = differ.currentList[position]
        holder.bind(user)
    }

    inner class StockViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtTitle: EditText = itemView.findViewById(R.id.stock_name)
        private val txtCount: EditText = itemView.findViewById(R.id.stock_count)
        private val txtPrice: EditText = itemView.findViewById(R.id.stock_price)
        private val btnRemove: ImageButton = itemView.findViewById(R.id.stock_remove)
        private val imgStockItem: ImageView = itemView.findViewById(R.id.stock_img)

        fun bind(item: StockItem){
            txtTitle.setText(item.title)
            txtCount.setText(item.count.toString())
            txtPrice.setText(item.price.toString())
            if(item.img != null)
                imgStockItem.setImageURI(item.img)

            val pos = adapterPosition
            btnRemove.setOnClickListener{
                    v -> itemClickListener?.onClick(v, pos)
            }
        }
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(v: View, pos: Int)
    }
}