package com.example.calculationseoulcomicland.data

import android.net.Uri

data class CalItem (
    val id : Int,
    val title : String,
    val count : Int,
    val price : Int,
    val author : String,
    val img : Uri?){

    fun calToString(stock : CalItem): String {
        val strCalID = stock.id.toString()
        val strCalName = stock.title
        val strCalCount = stock.count
        val strCalPrice = stock.price
        val strCalAuthor = stock.author
        val strCalImgURI = stock.img.toString()

        return strCalID + "_" + strCalName + "_" + strCalCount + "_" + strCalPrice + "_" + strCalImgURI
    }
}