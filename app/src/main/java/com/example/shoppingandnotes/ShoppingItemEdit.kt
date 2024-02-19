package com.example.shoppingandnotes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import java.text.DecimalFormat

class ShoppingItemEdit :ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_screen_shopping)

        val itemName=findViewById<EditText>(R.id.etNameShoppingItemEdit)
        val itemAmount=findViewById<EditText>(R.id.etAmountShoppingItemEdit)

        val filter=arrayOf( InputFilter{
                source, _, _, _, _, _ ->
            if (source != null && source.contains("\n")) {
                ""
            } else {
                null
            }
        })
        itemName.filters= filter

        fun removeKeyboard(){
            if (!itemName.hasFocus() && !itemAmount.hasFocus()) {
                val imm = itemName.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(itemName.windowToken, 0)
            }
        }

        itemName.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            removeKeyboard()
        }


        itemAmount.filters= filter

        itemAmount.onFocusChangeListener=View.OnFocusChangeListener { _, _ ->
            removeKeyboard()
        }

        val itemData = intent.getStringArrayListExtra("listaPodataka")

        if(itemData!=null){
            itemName.setText(itemData[0])
            itemAmount.setText(itemData[1])
        }

        val bSaveItem = findViewById<Button>(R.id.bSaveItem)
        bSaveItem.setOnClickListener{
            if(itemName.text.toString().isEmpty() || itemAmount.text.toString().isEmpty()){
                itemName.error="Morate unjeti ime artikla i količinu"
            }else{
                if(!itemData.isNullOrEmpty()){
                    ShoppingDataManager.getInstance().deleteItemById(itemData[2])
                }
                var pom = String.format("%.3f",itemAmount.text.toString().toDouble())
                pom=DecimalFormat("#.###").format(pom.toDouble())
                ShoppingDataManager.getInstance().addShoppingItem(ShoppingItem(name=itemName.text.toString(), amount = pom))
                startActivity(Intent(this, ShoppingListActivity::class.java))
                finish()

            }

        }

        val bBack = findViewById<Button>(R.id.bBackToList)
        bBack.setOnClickListener{
            if(!itemData.isNullOrEmpty() && (itemData[0]!=itemName.text.toString() || itemData[1]!=itemAmount.text.toString())){
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.dialoge,null)
                builder.setTitle("Jeste li sigurni da želite odbaciti promjene?")
                builder.setPositiveButton("Da"){_,_ ->
                    startActivity(Intent(this, ShoppingListActivity::class.java))
                    finish()
                }
                builder.setNegativeButton("Ne"){dialog,_->
                    dialog.dismiss()
                }
                builder.setView(dialogLayout)
                builder.show()
            }else{
                startActivity(Intent(this, ShoppingListActivity::class.java))
                finish()
            }
        }

        val bDelete = findViewById<Button>(R.id.bDeleteItem)
        if(itemData.isNullOrEmpty()){
            bDelete.visibility=View.GONE
        }else{
            bDelete.visibility=View.VISIBLE
        }

        bDelete.setOnClickListener{
            if(!itemData.isNullOrEmpty()){
                ShoppingDataManager.getInstance().deleteItemById(itemData[2])
            }
            startActivity(Intent(this, ShoppingListActivity::class.java))
            finish()
        }


    }

}