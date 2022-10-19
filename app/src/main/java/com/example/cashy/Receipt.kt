package com.example.cashy

import android.icu.util.TimeZone
import android.media.Image
import android.provider.MediaStore
import android.widget.Spinner
import android.widget.TimePicker
import com.google.firebase.firestore.DocumentId
import java.sql.Timestamp
import java.util.*

data class Receipt(
    val id: String = "",
    //var time =
                   //val category: Enum.Companion = Enum,
    @DocumentId var documentId: String?="",
    var sum: String = "", // Här ville jag ge en int- Användaren kan endast användas INT
    var company: String="",
    var notis: String="",
                    //var cashOrCard: Enum.Companion = Enum,
                    //var image : String = "",
                    //var spinner :Spinner
    )

/*enum class category(){
    FOODBEVARAGE,
    ENTERTAIMENT,
    NOJE,
    RESOR,
    BARN,

}
enum class cashOrCard(){
    CARD,
    CASH

}*/