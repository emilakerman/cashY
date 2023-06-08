package com.example.cashy

import com.google.firebase.firestore.DocumentId
import java.util.Calendar
import java.util.Date

data class Receipt(
    val id: String = "",
    @DocumentId var documentId: String?="",
    var sum: Int? = null, // 
    var company: String? = null,
    var notis: String? = null,
    var category: String? = null,
    var paymentMethod: String? = null,
    var timestamp: Date? = null,
    var img : Int? = null,
    var monthNo : String? = null, // saves the month
    var year : String? = null, // saves the year
    var day : String? = null,
    var fullDate: String?= null
) {
    init {
        setImageCategory()
        setTime()
    }

    private fun setTime() {
        val c=Calendar.getInstance()


        day= c.get(Calendar.DAY_OF_MONTH).toString().padStart(2,'0')
        monthNo= (c.get(Calendar.MONTH) + 1).toString()
        year= c.get(Calendar.YEAR).toString()

        fullDate="$day.$monthNo.$year"
    }

    private fun setImageCategory() {
        img = when (category) {
            "Groceries","Food" -> R.drawable.food_icon
            "Housing" -> R.drawable.house_icon
            "Electronics" -> R.drawable.electronics_icon
            "Sports" -> R.drawable.sports_icon
            "Travel" -> R.drawable.travel_icon
            "Fika" -> R.drawable.coffee_icon
            "Entertainment" -> R.drawable.entertainment
            "Food and Beverage" -> R.drawable.foodbeverage
            "Lifestyle" -> R.drawable.lifestyle
            "Transport" -> R.drawable.ic_baseline_directions_transit_24
            "Household" -> R.drawable.ic_baseline_house_24
            else -> R.drawable.other
        }
    }
}