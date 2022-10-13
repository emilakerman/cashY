package com.example.cashy

import android.icu.util.TimeZone
import android.media.Image
import android.provider.MediaStore
import android.widget.TimePicker
import java.sql.Timestamp
import java.util.*

class Receipt() {

    val id : String = ""
    //var time =
    val category = Enum
    val sum : Int =0
    var cashOrCard = Enum
    var image : String = ""
    var notis : String=""


}

enum class category(){
    FOODBEVARAGE,
    ENTERTAIMENT,
    NOJE,
    RESOR,
    BARN,

}
enum class cashOrCard(){
    CARD,
    CASH

}