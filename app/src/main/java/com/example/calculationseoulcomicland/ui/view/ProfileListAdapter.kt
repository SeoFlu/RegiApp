package com.example.calculationseoulcomicland.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.Profile
import com.example.calculationseoulcomicland.databinding.ProfileListItemViewBinding

class ProfileListAdapter (val itemList: ArrayList<Profile>) :
    RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder>() {

    private var LOG_TAG = "ProfileListAdapter."

    private var itemArrList : ArrayList<Profile> = ArrayList<Profile>()
    private lateinit var itemClickListener : OnItemClickListener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileListAdapter.ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_list_item_view, parent, false)
        itemArrList.addAll(itemList)
        return ProfileViewHolder(ProfileListItemViewBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.binding
        if(itemArrList.isNotEmpty()) {
            // set profile img
            holder.binding.profileName.setText(itemArrList.get(position).name)
            holder.binding.profileAdd.setOnClickListener{
                Log.d(LOG_TAG, "onBindViewHolder: profileAdd")
            }
            holder.binding.profileRemove.setOnClickListener{
                Log.d(LOG_TAG, "onBindViewHolder: profileRemove")
            }
            holder.binding.profileModify.setOnClickListener{
                Log.d(LOG_TAG, "onBindViewHolder: profileModify")
            }
        }
    }

    override fun getItemCount(): Int {
        return itemArrList.count()
    }

    fun updateList(itemList: ArrayList<Profile>) {
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
            arrayListStr += (itemArrList[i].name + "_")
            arrayListStr += (itemArrList[i].image.toString())
            if(i != itemArrList.size - 1){
                arrayListStr += "&"
            }
        }
        return arrayListStr
    }

    class ProfileViewHolder(val binding : ProfileListItemViewBinding) : RecyclerView.ViewHolder(binding.root){
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


    interface OnItemClickListener {
        fun onClick(v: View, pos: Int)
    }
}