package com.example.shoppingandnotes

class NotesDataManager private constructor() {

    private val noteItems: MutableList<NoteItem> = mutableListOf(
        NoteItem(title = "Bilješka 1", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 2", note = "Life is a series of moments, some grand and others seemingly insignificant. " +
                "But it's often the smallest moments that carry the most meaning. " +
                "Take a moment today to appreciate the little things – a smile from a stranger, a warm cup of tea, " +
                "or the beauty of a sunset. In these simple moments, you'll find the true essence of life's beauty and wonder."+ "Life is a series of moments, some grand and others seemingly insignificant. " +
                "But it's often the smallest moments that carry the most meaning. " +
                "Take a moment today to appreciate the little things – a smile from a stranger, a warm cup of tea, " +
                "or the beauty of a sunset. In these simple moments, you'll find the true essence of life's beauty and wonder."+"Life is a series of moments, some grand and others seemingly insignificant. " +
                "But it's often the smallest moments that carry the most meaning. " +
                "Take a moment today to appreciate the little things – a smile from a stranger, a warm cup of tea, " +
                "or the beauty of a sunset. In these simple moments, you'll find the true essence of life's beauty and wonder."+"Life is a series of moments, some grand and others seemingly insignificant. " +
                "But it's often the smallest moments that carry the most meaning. " +
                "Take a moment today to appreciate the little things – a smile from a stranger, a warm cup of tea, " +
                "or the beauty of a sunset. In these simple moments, you'll find the true essence of life's beauty and wonder."+"Life is a series of moments, some grand and others seemingly insignificant. " +
                "But it's often the smallest moments that carry the most meaning. " +
                "Take a moment today to appreciate the little things – a smile from a stranger, a warm cup of tea, " +
                "or the beauty of a sunset. In these simple moments, you'll find the true essence of life's beauty and wonder."),
        NoteItem(title = "Bilješka 3", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 4", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 5", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 6", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 7", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 8", note = "Life is a series of moments, some grand and others seemingly insignificant. "),
        NoteItem(title = "Bilješka 9", note = "Life is a series of moments, some grand and others seemingly insignificant. ")

    )

    companion object {
        @Volatile
        private var instance: NotesDataManager? = null

        fun getInstance(): NotesDataManager {
            return instance ?: synchronized(this) {
                instance ?: NotesDataManager().also { instance = it }
            }
        }
    }

    fun addNoteItem(item: NoteItem) {
        noteItems.add(item)
        sortNoteItems()
    }


    fun getNoteItems(): MutableList<NoteItem> {
        return noteItems
    }

    fun deleteNoteItemById(itemId: String) {
        val position = noteItems.indexOfFirst { it.id == itemId }
        if (position != -1) {
            noteItems.removeAt(position)
        }
    }

    fun getNoteItemById(itemId:String):NoteItem{
        val position = noteItems.indexOfFirst { it.id == itemId }
        return noteItems[position]
    }


    private fun sortNoteItems() {
        noteItems.sortWith(compareBy<NoteItem>{ it.title }.thenBy{ it.listOfLinkedSI.size }.thenByDescending { it.id })
    }

}