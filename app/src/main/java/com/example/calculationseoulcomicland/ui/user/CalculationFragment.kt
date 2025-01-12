package com.example.calculationseoulcomicland.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculationseoulcomicland.MainActivity.Companion.preferences
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.data.StockItem
import com.example.calculationseoulcomicland.ui.view.CalListAdapter
import com.example.calculationseoulcomicland.util.DataUtil
import com.example.calculationseoulcomicland.util.PreferenceUtil

class CalculationFragment : Fragment() {

    var itemList : ArrayList<StockItem> = ArrayList<StockItem>()
    val calListAdapter = CalListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TAG", "onCreateView: ")
        val view : View = inflater.inflate(R.layout.fragment_calculation, container, false)

        initPreference()
        initData()

        initView(view)

        return view
    }

    private fun initView(view : View){
        val calList : RecyclerView = view.findViewById(R.id.cal_recycler_view)
        val calBtn : Button = view.findViewById(R.id.cal_success)
        val calTotal : TextView = view.findViewById(R.id.cal_total)

        calListAdapter.notifyDataSetChanged()

        calList.adapter = calListAdapter
        calList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        calBtn.setOnClickListener{
//            preferences.setString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, stockListAdapter.getListData())
        }
    }

    private fun initPreference(){
        Log.d("TAG", "initPreference: ")
        preferences = PreferenceUtil(requireActivity())
    }

    private fun initData() {
        Log.d("TAG", "initData: ")
        var arrItems : ArrayList<StockItem> = DataUtil.StringToStockItemList(preferences.getString(DefineValue.PREFERENCE_KEY_STOCK_INFO_STRING, ""))
        Log.d("TAG", "initData: "+arrItems.toString())
        if(arrItems.isNotEmpty())
            calListAdapter.updateList(arrItems)
    }
}