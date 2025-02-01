package com.example.calculationseoulcomicland.data

import android.net.Uri

data class Profile (val id : Int, val name : String, val image : Uri?){

    fun profileToString(profile: Profile): String {
        val strProfileID = profile.id.toString()
        val strProfileName = profile.name
        val strProfileURI = profile.image.toString()

        return strProfileID + "_" + strProfileName + "_" + strProfileURI
    }
}