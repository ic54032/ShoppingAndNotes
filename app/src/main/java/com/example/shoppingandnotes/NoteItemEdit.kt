package com.example.shoppingandnotes

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class NoteItemEdit:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteOldData = intent.getStringArrayListExtra("straiPodaciOBilješki")
        if(!noteOldData.isNullOrEmpty()){
            overridePendingTransition(0, 0);
        }
        setContentView(R.layout.edit_screen_notes)

        val bBackToNoteList = findViewById<Button>(R.id.bBackToNotesList)
        val noteData = intent.getStringArrayListExtra("listaPodatakaOBilješki")
        val etNoteTitle = findViewById<EditText>(R.id.etNoteTitle)
        val etNote = findViewById<EditText>(R.id.etNote)
        val bDeleteNotes = findViewById<Button>(R.id.bDeleteNote)
        val bSaveNote = findViewById<Button>(R.id.bSaveNote)
        val bAddItemToNote = findViewById<Button>(R.id.bAddShoppingItemToNote)

        val noteAfterAddItem=intent.getStringArrayListExtra("bilješkaPoslijeDodavanjaProizvoda")


        etNoteTitle.filters= arrayOf( InputFilter{
                source, _, _, _, _, _ ->
            if (source != null && source.contains("\n")) {
                ""
            } else {
                null
            }
        })

        fun removeKeyboard(){
            if (!etNote.hasFocus() && !etNoteTitle.hasFocus()) {
                val imm = etNoteTitle.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etNoteTitle.windowToken, 0)
            }
        }

        etNoteTitle.onFocusChangeListener=View.OnFocusChangeListener { _, _ ->
            removeKeyboard()
        }

        etNote.onFocusChangeListener=View.OnFocusChangeListener { _, _ ->
            removeKeyboard()
        }


        var id=""
        if(!noteOldData.isNullOrEmpty()){
            id=noteOldData[2]
            etNoteTitle.setText(noteOldData[0])
            etNote.setText(noteOldData[1])

        }else if(!noteData.isNullOrEmpty()){
            id=noteData[2]
            etNoteTitle.setText(noteData[0])
            etNote.setText(noteData[1])
        }else if(!noteAfterAddItem.isNullOrEmpty()){
            id=noteAfterAddItem[2]
            etNoteTitle.setText(noteAfterAddItem[0])
            etNote.setText(noteAfterAddItem[1])
        }
        else{
            bDeleteNotes.visibility= View.GONE
        }

        bDeleteNotes.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialoge,null)
            builder.setTitle("Jeste li sigurni da želite obrisati bilješku ?")
            builder.setPositiveButton("Da"){ _, _ ->
                NotesDataManager.getInstance().deleteNoteItemById(id)
                startActivity(Intent(this,NotesActivity::class.java))
                finish()
            }
            builder.setNegativeButton("Ne"){ dialog, _ ->
                dialog.dismiss()
            }
            builder.setView(dialogLayout)
            builder.show()
        }

        bSaveNote.setOnClickListener {
            bSaveNote.background= ColorDrawable(Color.parseColor("#F3F3F7"))
            if(etNoteTitle.text.toString().isEmpty() || etNote.text.toString().isEmpty()){
                bSaveNote.background= null
                etNoteTitle.error="Morate unjeti naslov i tijelo bilješke kako bi ju mogli spremiti"
            }else{
                NotesDataManager.getInstance().deleteNoteItemById(id)
                etNoteTitle.clearFocus()
                etNote.clearFocus()
                val linked= checkReference(etNote.text.toString()).toSet().toMutableList()

                var newNote=NoteItem(title = etNoteTitle.text.toString(), note = etNote.text.toString())
                if(linked.size!=0){
                    newNote=NoteItem(title = etNoteTitle.text.toString(), note = etNote.text.toString(), listOfLinkedSI = linked)
                }
                NotesDataManager.getInstance().addNoteItem(newNote)
                val intent = Intent(this@NoteItemEdit,NoteItemEdit::class.java)
                val stringArray=ArrayList<String>()
                stringArray.add(newNote.title)
                stringArray.add(newNote.note)
                stringArray.add(newNote.id)
                intent.putStringArrayListExtra("straiPodaciOBilješki", stringArray)
                startActivity(intent)
            }
        }

        bBackToNoteList.setOnClickListener {

            if(
                (!noteOldData.isNullOrEmpty() && (noteOldData[0]!=etNoteTitle.text.toString() || noteOldData[1]!=etNote.text.toString())) ||
                (!noteData.isNullOrEmpty() && (noteData[0]!=etNoteTitle.text.toString() || noteData[1]!=etNote.text.toString())) ||
                (!noteAfterAddItem.isNullOrEmpty() && id!="" && (NotesDataManager.getInstance().getNoteItemById(id).note!=etNote.text.toString() || noteAfterAddItem[0]!=etNoteTitle.text.toString()))

                ){
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.dialoge,null)
                builder.setTitle("Jeste li sigurni da želite odbaciti promjene?")
                builder.setPositiveButton("Da"){ _, _ ->
                    startActivity(Intent(this, NotesActivity::class.java))
                    finish()
                }
                builder.setNegativeButton("Ne"){ dialog, _ ->
                    dialog.dismiss()
                }
                builder.setView(dialogLayout)
                builder.show()
            } else{
                startActivity(Intent(this, NotesActivity::class.java))
            }
        }

        bAddItemToNote.setOnClickListener {
            val intent=Intent(this,ChooseShoppingItem::class.java)
            val stringArray=ArrayList<String>()
            stringArray.add(etNoteTitle.text.toString())
            stringArray.add(etNote.text.toString())
            stringArray.add(id)
            intent.putStringArrayListExtra("bilješkaPrijeDodavanjaProizvoda", stringArray)
            startActivity(intent)
            finish()
        }

        val bSeeAddedItems = findViewById<Button>(R.id.bSeeAddedItems)
        bSeeAddedItems.setOnClickListener {
            val intent=Intent(this,AddedItemsDialoge::class.java)
            if(id!=""){
                val noteItem = NotesDataManager.getInstance().getNoteItemById(id)
                intent.putParcelableArrayListExtra("listaDodanih",ArrayList(noteItem.listOfLinkedSI))
            }else{
                intent.putParcelableArrayListExtra("listaDodanih", ArrayList())
            }
            startActivity(intent)

        }

    }

    //kako provjerit broj referenciranih proizvoda
    private fun checkReference(input:String,character:String="~"):MutableList<ShoppingItem>{
        var currentIndex = -1
        var lastIndex = -1
        var list= mutableListOf<ShoppingItem>()
        var pom = ""
        do {
            currentIndex = input.indexOf(character, lastIndex + 1)
            if (currentIndex != -1) {
                val textAfterCharacter = input.substring(currentIndex + 1)
                if(textAfterCharacter.indexOf(" ")!=-1){
                    pom = textAfterCharacter.substring(0,textAfterCharacter.indexOf(" ")).replace("_"," ")
                }else{
                    pom = textAfterCharacter.substring(0,textAfterCharacter.length).replace("_"," ")
                }
                lastIndex = currentIndex

                val  item = ShoppingDataManager.getInstance().getShoppingItems().find {
                    it.name == pom
                }
                if(item!=null ){
                    list.add(item)
                }
            }
        } while (currentIndex != -1)
        return list
    }

}