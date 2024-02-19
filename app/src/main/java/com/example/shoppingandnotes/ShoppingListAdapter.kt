package com.example.shoppingandnotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ShoppingListAdapter(
    private val shoppingItems: MutableList<ShoppingItem>,
): RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>(){
    private var onClickListener: OnClickListener? = null
    class ShoppingListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        return ShoppingListViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_shopping,parent,false
        ))
    }


    override fun getItemCount(): Int {
        return shoppingItems.size
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val currentShoppingItem=shoppingItems[position]
        holder.itemView.apply {
            val tvNameShoppingItem = findViewById<TextView>(R.id.tvNameShoppingItem)
            val tvAmountShoppingItem = findViewById<TextView>(R.id.tvAmountShoppingItem)
            var pom=currentShoppingItem.name
            if(currentShoppingItem.name.length>40){
                pom = (currentShoppingItem.name).substring(0,currentShoppingItem.name.length-15)+"..."
            }
            tvNameShoppingItem.text=pom
            tvAmountShoppingItem.text= currentShoppingItem.amount
        }
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentShoppingItem)
            }
        }

    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: ShoppingItem)
    }

}