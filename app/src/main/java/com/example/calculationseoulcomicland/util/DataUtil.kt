package com.example.calculationseoulcomicland.util

import com.example.calculationseoulcomicland.data.StockItem

object DataUtil {

    public fun StringToStockItemList(string : String): ArrayList<StockItem> {
        var arrStockItem : ArrayList<StockItem> = arrayListOf<StockItem>()

        if(string.equals(""))
            return arrStockItem

        if(string.contains("&")){
            val tempStringArr = string.split("&")
            for(tempString in tempStringArr){
                var tempStockItemArr = tempString.split("_")
                arrStockItem.add(StockItem(tempStockItemArr.get(0),tempStockItemArr.get(1).toInt(), tempStockItemArr.get(2).toInt()))
            }
        } else {
            var tempStockItemArr = string.split("_")
            arrStockItem.add(StockItem(tempStockItemArr.get(0),tempStockItemArr.get(1).toInt(), tempStockItemArr.get(2).toInt()))
        }

        return arrStockItem
    }
}