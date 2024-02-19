package com.example.shoppingandnotes


class NoteItem (
    val id : String = generateUniqueId().toString(),
    var title : String,
    var note : String = "",
    var listOfLinkedSI : MutableList<ShoppingItem> = arrayListOf(),
    val creation_time : String = date()
){
    override fun toString(): String {
        return title
    }
}