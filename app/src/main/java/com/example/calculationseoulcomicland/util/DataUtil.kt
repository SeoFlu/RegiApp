package com.example.calculationseoulcomicland.util

import android.net.Uri
import com.example.calculationseoulcomicland.data.Profile
import com.example.calculationseoulcomicland.data.StockItem
import java.net.URI

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

    public fun StringToProfileItemList(string : String): ArrayList<Profile> {
        var arrProfileItem : ArrayList<Profile> = arrayListOf<Profile>()

        if(string.equals(""))
            return arrProfileItem

        if(string.contains("&")){
            val tempStringArr = string.split("&")
            for(tempString in tempStringArr){
                var tempProfileItemArr = tempString.split("_")
                arrProfileItem.add(Profile(tempProfileItemArr.get(0).toInt(),tempProfileItemArr.get(1),  Uri.parse(tempProfileItemArr.get(2))))
            }
        } else {
            var tempProfileItemArr = string.split("_")
            arrProfileItem.add(Profile(tempProfileItemArr.get(0).toInt(),tempProfileItemArr.get(1), Uri.parse(tempProfileItemArr.get(2))))
        }

        return arrProfileItem
    }
}