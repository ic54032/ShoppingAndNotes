package com.example.shoppingandnotes

class ShoppingDataManager private constructor() {

    val pocetnaLista=mutableListOf(
        ShoppingItem(name="Ananas", amount = "2"),
        ShoppingItem(name="Banana",amount="5"),
        ShoppingItem(name="Kava Franck Jubilarna", amount = "1"),
        ShoppingItem(name="Kiselo zelje",amount="1"),
        ShoppingItem(name="Mlijeko Dukat i ostale stvari napisane za mljeko", amount = "4"),
        ShoppingItem(name="Nutella", amount="3"),
        ShoppingItem(name="Sir Gauda",amount="10.555"),
        ShoppingItem(name="Šunka u ovitku",amount="2.55"),
        ShoppingItem(name="Tortilja", amount = "5")
        )

    private val shoppingItems: MutableList<ShoppingItem> = pocetnaLista

    companion object {
        @Volatile
        private var instance: ShoppingDataManager? = null

        fun getInstance(): ShoppingDataManager {
            return instance ?: synchronized(this) {
                instance ?: ShoppingDataManager().also { instance = it }
            }
        }
    }

    fun addShoppingItem(item: ShoppingItem) {
        shoppingItems.add(item)
        sortShoppingItems()
    }


    fun getShoppingItems(): MutableList<ShoppingItem> {
        return shoppingItems
    }

    fun deleteItemById(itemId: String) {
        val position = shoppingItems.indexOfFirst { it.id == itemId }
        if (position != -1) {
            shoppingItems.removeAt(position)
        }
    }


    private fun sortShoppingItems() {
        shoppingItems.sortWith(compareBy<ShoppingItem>{it.name }.thenByDescending{ it.amount })
    }
}