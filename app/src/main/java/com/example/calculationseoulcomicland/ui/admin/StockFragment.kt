package com.example.calculationseoulcomicland.ui.admin

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calculationseoulcomicland.MainActivity.Companion.preferences
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.data.Profile
import com.example.calculationseoulcomicland.data.StockItem
import com.example.calculationseoulcomicland.ui.view.StockListAdapter
import com.example.calculationseoulcomicland.util.DataUtil
import com.example.calculationseoulcomicland.util.PreferenceUtil
import com.example.calculationseoulcomicland.databinding.FragmentStockBinding

class StockFragment : Fragment() {

    private val LOG_TAG = "StockFragment."
    private val STOCK_LIST_ITEM_WIDTH = 190

    private var mBinding: FragmentStockBinding? = null
    private val binding get() = mBinding!!
    private val arrItems = mutableListOf<StockItem>()
    private lateinit var stockListAdapter: StockListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentStockBinding.inflate(inflater, container, false)
        initView(binding)
        initPreference()
        initData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null;
    }

    private fun initView(view : FragmentStockBinding){
        stockListAdapter = StockListAdapter(context!!)
        mBinding!!.stockRecyclerView.layoutManager =
            GridLayoutManager(context, getCountItemFromWidth())
        stockListAdapter.setItemClickListener(object :
            StockListAdapter.OnItemClickListener {
            override fun onClick(v: View, pos: Int) {
                when (v.id) {
                    R.id.stock_remove -> {
                        Log.d(LOG_TAG, "onClick: remove $pos ")
                        removeStockItem(pos)
                    }
                }
            }
        }
        )
        mBinding!!.stockRecyclerView.adapter = stockListAdapter

        mBinding!!.stockAddListItem.setOnClickListener{
            addStockListItem()
        }

        var strProfileInfo = preferences.getString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, "")
        if(strProfileInfo != ""){
            var arrProfileInfo : ArrayList<Profile> = DataUtil.StringToProfileItemList(strProfileInfo)
            var arrProfileAuthor = arrayListOf<String>()

            for( tempProfile in arrProfileInfo ){
                arrProfileAuthor.add(tempProfile.name)
            }

            var adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item , arrProfileAuthor)
            mBinding!!.stockAddView.stockAddItemAuthorSpinner.adapter = adapter
            mBinding!!.stockAddView.stockAddItemAuthorSpinner.setSelection(0)
        }
    }

    private fun addStockListItem(){
        var info : String
        mBinding?.stockAddView?.root?.visibility = View.VISIBLE
        mBinding?.stockAddView?.stockClose?.setOnClickListener{
            mBinding?.stockAddView?.root?.visibility = View.GONE
        }
        mBinding?.stockAddView?.stockAdd?.setOnClickListener{
            mBinding?.stockAddView?.root?.visibility = View.GONE
            val itemName : String = mBinding?.stockAddView?.stockAddItemTitle?.text.toString()
            val itemPrice : String = mBinding?.stockAddView?.stockAddItemPrice?.text.toString()
            val itemAuthor : String = mBinding?.stockAddView?.stockAddItemAuthorSpinner?.selectedItem.toString()
//            Log.d(LOG_TAG, "addStockListItem item : $itemName / $itemPrice / $itemAuthor")

            val stockString = preferences.getString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, "")

            if(stockString == ""){
                val stock = StockItem(1, itemName, 0, itemPrice.toInt(), itemAuthor, null)
                info = stock.stockToString(stock)
            } else {
                val stock = StockItem(DataUtil.StringToStockItemList(stockString).size+1, itemName, 0, itemPrice.toInt(), itemAuthor, null)
                info = "$stockString&${stock.stockToString(stock)}"
            }

            preferences.setString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, info)
            resetListItem()

        }
        mBinding?.stockAddView?.stockAddItemTitle?.setText("")
        mBinding?.stockAddView?.stockAddItemPrice?.setText("")
    }

    private fun removeStockItem(pos : Int){
        Log.d(LOG_TAG, "removeStockItem() $pos")
        val stockPrefString = preferences.getString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, "")
        val stockItemArr = DataUtil.StringToStockItemList(stockPrefString)
        if(pos < stockItemArr.size)
            stockItemArr.removeAt(pos)

        preferences.setString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, DataUtil.StockItemListToString(stockItemArr))
        resetListItem()
    }

    private fun getCountItemFromWidth(): Int{
        var itemCount = 1

        val display = this.context?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        itemCount = px2dp(deviceWidth!!, this.context!!).toInt() / STOCK_LIST_ITEM_WIDTH
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
        preferences = PreferenceUtil(requireActivity())
    }

    private fun initData() {
//        preferences.setString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, "")
        resetListItem()
    }

    private fun resetListItem(){

        arrItems.apply {
            arrItems.clear()

            val arrStockItem = DataUtil.StringToStockItemList( preferences.getString(
                DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, ""))
            for( item in arrStockItem ){
                add(item)
            }
            stockListAdapter.differ.submitList(arrItems)

            Log.d(LOG_TAG, "resetListItem arrItems : "+arrItems)
            stockListAdapter.notifyDataSetChanged()
        }
    }

}