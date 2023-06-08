package com.example.cashy

data class Expenses(var category: String, var transactions: Int,var img: Int?=null) {

    init {
        setImgCategory()
    }

    fun setImgCategory() {
        when (category) {
            "Food" -> img=R.drawable.food_icon
            "Clothes" -> img=R.drawable.clothes_icon
            "Films" -> img=R.drawable.movies_icon
            "Furniture" -> img=R.drawable.housing_icon
            "House Bills" -> img=R.drawable.house_icon
            "Transport" -> img=R.drawable.transport
        }
    }
}