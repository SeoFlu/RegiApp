package com.example.calculationseoulcomicland.util

import android.net.Uri
import androidx.core.net.toUri
import com.example.calculationseoulcomicland.data.Profile
import com.example.calculationseoulcomicland.data.StockItem
import java.net.URI

object DataUtil {

    fun StringToStockItemList(string : String): ArrayList<StockItem> {
        var arrStockItem : ArrayList<StockItem> = arrayListOf<StockItem>()

        if(string.equals(""))
            return arrStockItem

        if(string.contains("&")){
            val tempStringArr = string.split("&")
            for(tempString in tempStringArr){
                var tempStockItemArr = tempString.split("_")
                arrStockItem.add(
                    StockItem(
                        tempStockItemArr.get(0).toInt(),
                        tempStockItemArr.get(1),
                        tempStockItemArr.get(2).toInt(),
                        tempStockItemArr.get(3).toInt(),
                        tempStockItemArr.get(4),
                        tempStockItemArr.get(5).toUri()
                    )
                )
            }
        } else {
            var tempStockItemArr = string.split("_")
            arrStockItem.add(
                StockItem(
                    tempStockItemArr.get(0).toInt(),
                    tempStockItemArr.get(1),
                    tempStockItemArr.get(2).toInt(),
                    tempStockItemArr.get(3).toInt(),
                    tempStockItemArr.get(4),
                    tempStockItemArr.get(5).toUri()
                )
            )
        }

        return arrStockItem
    }

    fun StockItemListToString(arrItem : ArrayList<StockItem>) : String {
        var arrStockItems: ArrayList<StockItem> = arrItem
        var strStockItems: String = ""

        if (arrStockItems.size != 0) {
            for (stockItem in arrStockItems) {
                val strInfo =
                    stockItem.id.toString() + "_" +
                            stockItem.title + "_" +
                            stockItem.count.toString() + "_" +
                            stockItem.price.toString() + "_" +
                            stockItem.author + "_" +
                            stockItem.img.toString()

                strStockItems += strInfo
                if (arrStockItems.last() == stockItem)
                    break;
                strStockItems += "&"
            }
        }

        return strStockItems
    }

    fun StringToProfileItemList(string : String): ArrayList<Profile> {
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

    fun ProfileItemListToString(arr : ArrayList<Profile>) : String {
        var arrProfileItem: ArrayList<Profile> = arr
        var strProfiles: String = ""

        if (arrProfileItem.size != 0) {
            for (profile in arrProfileItem) {
                val strInfo = profile.id.toString() + "_" + profile.name + "_" + profile.image
                strProfiles += strInfo
                if (arrProfileItem.last() == profile)
                    break;
                strProfiles += "&"
            }
        }

        return strProfiles
    }
}