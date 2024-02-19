package com.example.shoppingandnotes
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class ChooseShoppingItem : ComponentActivity(){
    private lateinit var adapter: ShoppingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_item_choose)

        val note = intent.getStringArrayListExtra("bilješkaPrijeDodavanjaProizvoda")

        adapter= ShoppingListAdapter(ShoppingDataManager.getInstance().getShoppingItems())

        val stringArray=ArrayList<String>()
        if(!note.isNullOrEmpty()){
            stringArray.add(note[0])
            stringArray.add(note[1].toString())
            stringArray.add(note[2].toString())
        }

        if (note != null) {
            setListenerToAdapter(adapter = adapter, note = note, stringArray = stringArray)
        }


        val rvChooseShoppingItemFromList = findViewById<RecyclerView>(R.id.rvChooseShoppingItemFromList)
        rvChooseShoppingItemFromList.adapter=adapter
        rvChooseShoppingItemFromList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val searchEditText = findViewById<EditText>(R.id.etSearch)

        val bBackToNoteEdit = findViewById<Button>(R.id.bBackToNoteEdit)

        bBackToNoteEdit.setOnClickListener {
            val intent = Intent(this@ChooseShoppingItem,NoteItemEdit::class.java)
            intent.putStringArrayListExtra("bilješkaPoslijeDodavanjaProizvoda", stringArray)
            startActivity(intent)
            finish()
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                val list =  ShoppingDataManager.getInstance().getShoppingItems()
                val filteredList = list.filter{ it.name.contains(searchText, ignoreCase = true) }.toMutableList()

                val adapter = ShoppingListAdapter(filteredList)
                rvChooseShoppingItemFromList.adapter=adapter
                if (note != null) {
                    setListenerToAdapter(adapter=adapter,note=note,stringArray=stringArray)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }
    private fun setListenerToAdapter(adapter:ShoppingListAdapter,note:ArrayList<String>,stringArray:ArrayList<String>){
        adapter.setOnClickListener(object : ShoppingListAdapter.OnClickListener
        {
            override fun onClick(position: Int, model: ShoppingItem) {
                val intent = Intent(this@ChooseShoppingItem,NoteItemEdit::class.java)
                stringArray[1]=note[1] + " ~"+model.name.replace(" ","_")
                intent.putStringArrayListExtra("bilješkaPoslijeDodavanjaProizvoda", stringArray)
                startActivity(intent)
            }
        })
    }
}