package com.example.shoppingandnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotesActivity : ComponentActivity() {
    private lateinit var adapter: NotesListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_main)

        adapter=NotesListAdapter(NotesDataManager.getInstance().getNoteItems())

        adapter.setOnClickListener(object : NotesListAdapter.OnClickListener{
            override fun onClick(position: Int, model: NoteItem) {
                val intent = Intent(this@NotesActivity,NoteItemEdit::class.java)
                val stringArray=ArrayList<String>()
                stringArray.add(model.title)
                stringArray.add(model.note)
                stringArray.add(model.id)
                intent.putStringArrayListExtra("listaPodatakaOBilješki", stringArray)
                startActivity(intent)
            }

        })

        val rvNotesItemsList = findViewById<RecyclerView>(R.id.rvNotesItemsList)
        rvNotesItemsList.adapter=adapter
        rvNotesItemsList.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val bGoToShoppingList = findViewById<Button>(R.id.bGoToShoppingList)
        bGoToShoppingList.setOnClickListener{
            startActivity(Intent(this,ShoppingListActivity::class.java))
        }

        val bAddNoteItem = findViewById<Button>(R.id.bAddNotesItem)
        bAddNoteItem.setOnClickListener {
            startActivity(Intent(this,NoteItemEdit::class.java))
        }
    }

}