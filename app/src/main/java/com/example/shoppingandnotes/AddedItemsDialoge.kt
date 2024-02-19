package com.example.shoppingandnotes

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddedItemsDialoge : ComponentActivity() {
    private lateinit var adapter: ShoppingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_added)


        val tvNoLinkedItemsLabel = findViewById<TextView>(R.id.tvNoLinkedItemsLabel)
        val list = intent.getParcelableArrayListExtra<ShoppingItem>("listaDodanih")!!.toMutableList()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        list.sortWith(compareBy<ShoppingItem>{it.name}.thenByDescending { dateFormat.parse(it.creationTime) }.thenByDescending { it.id })
        val rvListOfAdded = findViewById<RecyclerView>(R.id.rvListOfAdded)
        if(list.isNullOrEmpty()){
            rvListOfAdded.visibility=View.GONE
            tvNoLinkedItemsLabel.visibility=View.VISIBLE
        }else{
            rvListOfAdded.visibility=View.VISIBLE
            tvNoLinkedItemsLabel.visibility=View.GONE
            adapter = ShoppingListAdapter(list)
            rvListOfAdded.adapter=adapter
            rvListOfAdded.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        val bOk = findViewById<Button>(R.id.bOk)
        bOk.setOnClickListener {
            finish()
        }
    }
}