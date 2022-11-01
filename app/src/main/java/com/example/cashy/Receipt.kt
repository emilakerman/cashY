package com.example.cashy

import android.widget.Spinner
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Receipt(
    val id: String = "",
    //var time =
    //val category: Enum.Companion = Enum,
    @DocumentId var documentId: String?="",
    var sum: Int? = null, // Här ville jag ge en int- Användaren kan endast användas INT
    var company: String? = null,
    var notis: String? = null,
    var category: String? = null,
    var paymentmethod: String? = null,
    var timestamp: Date? = null,
    var img : Int? = null,
    //val catOfShop: Array<String> = arrayOf("Mat", "Nöje", "Fest", "Test", "Test1")

    //var img : Int? = null




    //var spinner :Spinner
) {
    init {
        setImageCategory()
    }

    fun setImageCategory() {
        when (category) {
            "Other" -> img = R.drawable.other
            "Entertainment" -> img = R.drawable.entertainment
            "Food & Beverage" -> img = R.drawable.foodbeverage
            "Lifestyle" -> img = R.drawable.lifestyle
            "Transport" -> img = R.drawable.ic_baseline_directions_transit_24
            else -> img = R.drawable.other
        }
    }

}



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