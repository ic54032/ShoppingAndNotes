package com.example.shoppingandnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShoppingListActivity : ComponentActivity() {
    private lateinit var adapter: ShoppingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_main)
        adapter= ShoppingListAdapter(ShoppingDataManager.getInstance().getShoppingItems())

        adapter.setOnClickListener(object : ShoppingListAdapter.OnClickListener
        {
            override fun onClick(position: Int, model: ShoppingItem) {
                val intent = Intent(this@ShoppingListActivity,ShoppingItemEdit::class.java)

                val stringArray=ArrayList<String>()
                stringArray.add(model.name)
                stringArray.add(model.amount)
                stringArray.add(model.id)
                intent.putStringArrayListExtra("listaPodataka", stringArray)
                startActivity(intent)
                finish()
            }
        })



        val rvShoppingItemsList=findViewById<RecyclerView>(R.id.rvShoppingItemsList)
        rvShoppingItemsList.hasFixedSize()
        rvShoppingItemsList.adapter=adapter
        rvShoppingItemsList.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val bAddItem = findViewById<Button>(R.id.bAddItem)
        bAddItem.setOnClickListener{
            startActivity(Intent(this, ShoppingItemEdit::class.java))
            finish()
        }

        val bGoToNotes = findViewById<Button>(R.id.bGoToNotes)
        bGoToNotes.setOnClickListener{
            startActivity(Intent(this,NotesActivity::class.java))
            finish()
        }
    }
}
