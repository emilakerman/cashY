package com.example.cashy

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
    var timestamp: Date? = null

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