package com.example.calculationseoulcomicland.ui.user

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calculationseoulcomicland.MainActivity
import com.example.calculationseoulcomicland.MainActivity.Companion.preferences
import com.example.calculationseoulcomicland.data.CalItem
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.data.StockItem
import com.example.calculationseoulcomicland.databinding.FragmentCalculationBinding
import com.example.calculationseoulcomicland.ui.view.CalListAdapter
import com.example.calculationseoulcomicland.util.DataUtil
import com.example.calculationseoulcomicland.util.PreferenceUtil

class CalculationFragment : Fragment() {

    private val LOG_TAG = "CalculationFragment."
    private val CAL_ITEM_LIST_ITEM_WIDTH = 190

    private var mBinding: FragmentCalculationBinding? = null
    private val binding get() = mBinding!!
    private val arrItems = mutableListOf<StockItem>()
    private lateinit var calListAdapter: CalListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TAG", "onCreateView: ")
        mBinding = FragmentCalculationBinding.inflate(inflater, container, false)
        initView(binding)
        initPreference()
        initData()

        return binding.root
    }

    private fun initView(view : FragmentCalculationBinding){
        calListAdapter = CalListAdapter(context!!)
        mBinding!!.calRecyclerView.layoutManager =
            GridLayoutManager(context, getCountItemFromWidth())
        calListAdapter.setItemClickListener(object :
            CalListAdapter.OnItemClickListener {
            override fun onClick(v: View, pos: Int) {
                when (v.id) {

                }
            }
        })

        mBinding!!.calRecyclerView.adapter = calListAdapter

    }

    private fun getCountItemFromWidth(): Int{
        var itemCount = 1

        val display = this.context?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        itemCount = px2dp(deviceWidth!!, this.context!!).toInt() / CAL_ITEM_LIST_ITEM_WIDTH
        Log.d(LOG_TAG, "getCountItemFromWidth: $itemCount $deviceWidth")

        if(itemCount < 1)
            return 1
        else
            return itemCount
    }

    fun px2dp(px: Int, context: Context): Float {
        return px / ((context.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
    }

    private fun initPreference(){
        Log.d("TAG", "initPreference: ")
        MainActivity.preferences.setBoolean(DefineValue.PREFERENCE_KEY_FRAGMENT_ADMIN, false)
        preferences = PreferenceUtil(requireActivity())
    }

    private fun initData() {
        Log.d("TAG", "initData: ")
        resetListItem()
    }

    private fun resetListItem(){

        arrItems.apply {
            arrItems.clear()

            val arrCalItems = DataUtil.StringToStockItemList( preferences.getString(
                DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, ""))
            for( item in arrCalItems ){
                add(item)
            }
            calListAdapter.differ.submitList(arrItems)

            Log.d(LOG_TAG, "resetListItem arrItems : "+arrItems)
            calListAdapter.notifyDataSetChanged()
        }
    }
}