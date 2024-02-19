package com.example.shoppingandnotes

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesListAdapter(
    private val notesList: MutableList<NoteItem>
) : RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {
    private var onClickListener: NotesListAdapter.OnClickListener? = null
    class NotesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        return NotesListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        val currentNoteItem=notesList[position]
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.tvNoteTitle)
            val preview = findViewById<TextView>(R.id.tvNotePreview)
            val linked = findViewById<TextView>(R.id.tvNumOfLinkedShoppingItems)

            title.text=currentNoteItem.title
            var lastChar=40
            if (currentNoteItem.note.length >= 40){
                preview.text= currentNoteItem.note.substring(0,lastChar).trim()+"..."
            }else{
                preview.text= currentNoteItem.note
            }
            // treba upisati substring
            linked.text=currentNoteItem.listOfLinkedSI.size.toString()
        }
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentNoteItem)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: NoteItem)
    }
}