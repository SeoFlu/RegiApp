package com.example.calculationseoulcomicland.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.Profile

class ProfileListAdapter (private val context: Context) :
    RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder>() {

    private var LOG_TAG = "ProfileListAdapter."

    private lateinit var itemClickListener : OnItemClickListener

    private val differCallback = object : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        Log.d(LOG_TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(context).inflate(R.layout.profile_list_item_view, parent, false)
        return ProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        Log.d(LOG_TAG, "onBindViewHolder: ")
        val user = differ.currentList[position]
        holder.bind(user)
    }

    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtName: TextView = itemView.findViewById(R.id.profile_name)
        private val btnModify: ImageButton = itemView.findViewById(R.id.profile_modify)
        private val btnRemove: ImageButton = itemView.findViewById(R.id.profile_remove)
        private val imgProfile: ImageView = itemView.findViewById(R.id.profile_image)

        fun bind(item: Profile){
            txtName.text = item.name;
            if(item.image != null)
                imgProfile.setImageURI(item.image)
        }
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


    interface OnItemClickListener {
        fun onClick(v: View, pos: Int)
    }
}