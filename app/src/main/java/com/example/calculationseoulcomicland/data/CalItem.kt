package com.example.calculationseoulcomicland.data

import android.net.Uri

data class StockItem (
    val id : Int,
    val title : String,
    val count : Int,
    val price : Int,
    val author : String,
    val img : Uri?){

    fun stockToString(stock : StockItem): String {
        val strStockID = stock.id.toString()
        val strStockName = stock.title
        val strStockCount = stock.count
        val strStockPrice = stock.price
        val strStockAuthor = stock.author
        val strStockImgURI = stock.img.toString()

        return strStockID + "_" + strStockName + "_" + strStockCount + "_" + strStockPrice + "_" + strStockAuthor + "_" + strStockImgURI
    }
}