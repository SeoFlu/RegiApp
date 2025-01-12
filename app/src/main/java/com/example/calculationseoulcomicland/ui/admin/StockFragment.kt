package com.example.calculationseoulcomicland.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculationseoulcomicland.MainActivity.Companion.preferences
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.data.StockItem
import com.example.calculationseoulcomicland.ui.view.StockListAdapter
import com.example.calculationseoulcomicland.util.DataUtil
import com.example.calculationseoulcomicland.util.PreferenceUtil
import com.example.calculationseoulcomicland.databinding.FragmentStockBinding

class StockFragment : Fragment() {

    private var mBinding: FragmentStockBinding? = null
    private val binding get() = mBinding!!

    var itemList : ArrayList<StockItem> = ArrayList<StockItem>()
    val stockListAdapter = StockListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentStockBinding.inflate(inflater, container, false)
        initPreference()
        initData()

        initView(binding)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mBinding = null;
    }

    private fun initView(view : FragmentStockBinding){

        stockListAdapter.notifyDataSetChanged()

        binding.stockRecyclerView.adapter = stockListAdapter
        binding.stockRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.addListItemBtn.setOnClickListener{
            itemList.add(StockItem("아무튼 굿즈 이름",0,0))
            stockListAdapter.updateList(itemList)
        }

        binding.removeListItemBtn.setOnClickListener{
            stockListAdapter.removeLastedItem()
        }

        binding.dataSaveBtn.setOnClickListener{
            preferences.setString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, stockListAdapter.getListData())
        }
    }

    private fun initPreference(){
        preferences = PreferenceUtil(requireActivity())
    }

    private fun initData() {
        var arrItems : ArrayList<StockItem> = DataUtil.StringToStockItemList(preferences.getString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, ""))
        if(arrItems.isNotEmpty())
            stockListAdapter.updateList(arrItems)
    }

}