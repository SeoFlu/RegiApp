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
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.CalItem
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.data.StockItem
import com.example.calculationseoulcomicland.databinding.FragmentCalculationBinding
import com.example.calculationseoulcomicland.ui.view.CalListAdapter
import com.example.calculationseoulcomicland.util.DataUtil
import com.example.calculationseoulcomicland.util.PreferenceUtil
import kotlin.math.log

class CalculationFragment : Fragment() {

    private val LOG_TAG = "CalculationFragment."
    private val CAL_ITEM_LIST_ITEM_WIDTH = 190

    private var mBinding: FragmentCalculationBinding? = null
    private val binding get() = mBinding!!
    private val arrItems = mutableListOf<StockItem>()
    private val arrItemsCount = mutableListOf<Int>()
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

            }

            override fun onClick(v: View, pos: Int, count: Int) {
                when (v.id) {
                    R.id.cal_item_count_plus, R.id.cal_item_img -> {
                        Log.d(LOG_TAG, "onClick: plus $pos ")
                        addCountItem(pos, count)
                        updateTotalPrice()
                    }
                    R.id.cal_item_count_minus -> {
                        Log.d(LOG_TAG, "onClick: minus $pos ")
                        minusCountItem(pos, count)
                        updateTotalPrice()
                    }
                }
            }
        })

        mBinding!!.calSuccess.setOnClickListener{
            Log.d(LOG_TAG, "onclick calSuccess")

            for(i in 0..arrItemsCount.size-1)
                arrItemsCount[i] = 0

            mBinding!!.calTotal.text = "0"
        }

        mBinding!!.calRecyclerView.adapter = calListAdapter
    }

    private fun updateTotalPrice(){
        var totalPrice = 0
        val arrCalItems = DataUtil.StringToStockItemList( preferences.getString(
            DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, ""))

        if(arrCalItems.isNotEmpty()){
            for (i in 0..arrCalItems.size-1)
            totalPrice += arrItemsCount.get(i) * arrCalItems.get(i).price
        }
        Log.d(LOG_TAG, "updateTotalPrice: $totalPrice")
        mBinding!!.calTotal.text = totalPrice.toString()
    }

    private fun addCountItem(pos : Int, currentCount: Int) : Int{
        Log.d(LOG_TAG, "addCountItem: ")
        val count : Int = currentCount
        arrItemsCount[pos] = currentCount

        return count
    }

    private fun minusCountItem(pos : Int, currentCount: Int) : Int{
        Log.d(LOG_TAG, "minusCountItem: ")
        val count : Int = currentCount
        arrItemsCount[pos] = currentCount
        return count
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
        preferences.setBoolean(DefineValue.PREFERENCE_KEY_FRAGMENT_ADMIN, false)
        preferences = PreferenceUtil(requireActivity())
    }

    private fun initData() {
        Log.d("TAG", "initData: ")
        resetListItem()

        for(i in 0..mBinding!!.calRecyclerView.adapter!!.itemCount ){
            arrItemsCount.add(0)
        }
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