package com.example.myrpgcharacter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.model.Ability

class AbilitiesAdapter(
    private var abilities: MutableList<Ability>,
    private val onEdit: (Ability) -> Unit,
    private val onDelete: (Ability) -> Unit
) : RecyclerView.Adapter<AbilitiesAdapter.AbilityViewHolder>() {

    class AbilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: EditText = view.findViewById(R.id.ability_title)
        val description: EditText = view.findViewById(R.id.ability_description)
        val editButton: Button = view.findViewById(R.id.edit_ability_button)
        val deleteButton: Button = view.findViewById(R.id.delete_ability_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ability, parent, false)
        return AbilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        val ability = abilities[position]
        holder.title.setText(ability.title)
        holder.description.setText(ability.description)

        // Clique para editar a habilidade
        holder.editButton.setOnClickListener {
            ability.title = holder.title.text.toString()
            ability.description = holder.description.text.toString()
            onEdit(ability)
        }

        // Clique para excluir a habilidade
        holder.deleteButton.setOnClickListener {
            onDelete(ability)
        }
    }

    override fun getItemCount() = abilities.size

    // Atualizar lista de habilidades
    fun updateAbilities(newAbilities: List<Ability>) {
        abilities.clear()
        abilities.addAll(newAbilities)
        notifyDataSetChanged()
    }
}
