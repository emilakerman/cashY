package com.example.cashy

object DataManager {
    var catBills= mutableListOf<Expenses>()

    init {
        createMockData()
    }

    fun createMockData() {
        catBills.add(Expenses("Food", 3))
        catBills.add(Expenses("Clothes", 3))
        catBills.add(Expenses("Films", 5))
        catBills.add(Expenses("Furniture", 10))
        catBills.add(Expenses("House Bills", 3))
        catBills.add(Expenses("Transport", 9))
        catBills.add(Expenses("Food", 3))
        catBills.add(Expenses("Clothes", 3))
        catBills.add(Expenses("Films", 5))
        catBills.add(Expenses("Furniture", 10))
        catBills.add(Expenses("House Bills", 3))
        catBills.add(Expenses("Transport", 9))
    }
}