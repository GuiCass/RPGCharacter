// CharacterAdapter.kt
package com.example.myrpgcharacter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.databaseHelper.DatabaseHelper
import com.example.myrpgcharacter.model.Character

class CharacterAdapter(
    private var characters: List<Character>,
    private val onEditClick: (Character) -> Unit,
    private val onDeleteClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.character_name)
        val deleteButton: Button = view.findViewById(R.id.button_delete_character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.nameTextView.text = character.name

        // Configurar o botão de editar
        holder.itemView.setOnClickListener { onEditClick(character) }

        // Configurar o botão de deletar
        holder.deleteButton.setOnClickListener {
            onDeleteClick(character)
        }
    }

    override fun getItemCount() = characters.size

    // Método para atualizar a lista após a exclusão
    fun updateCharacters(newCharacters: List<Character>) {
        this.characters = newCharacters
        notifyDataSetChanged()
    }
}