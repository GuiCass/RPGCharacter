package com.example.myrpgcharacter.fragment

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrpgcharacter.databaseHelper.DatabaseHelper
import com.example.myrpgcharacter.databinding.FragmentCharacterVisualizeBinding
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.adapter.AbilitiesAdapter
import com.example.myrpgcharacter.model.Ability

class CharacterFragmentVisualize : Fragment() {
    private var _binding: FragmentCharacterVisualizeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private var characterId: Int = -1
    private lateinit var abilitiesAdapter: AbilitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterVisualizeBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Obter o ID do personagem passado pelos argumentos
        characterId = arguments?.getInt("characterId") ?: -1

        // Verificar se o characterId é válido antes de carregar os dados
        if (characterId != -1) {
            loadCharacterData(characterId)
            setupAbilitiesSection()
        } else {
            Toast.makeText(requireContext(), "Erro ao carregar o personagem", Toast.LENGTH_SHORT).show()
        }

        // Configurar botões de edição e retorno
        binding.buttonEditar.setOnClickListener { editCharacter() }
        binding.buttonReturn.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragmentVisualize_to_characterListFragment)
        }
        binding.arrowLeft.setOnClickListener {
            val action = CharacterFragmentVisualizeDirections.actionCharacterFragmentVisualizeToCharacterImageFragment(characterId)
            findNavController().navigate(action)
        }

        setupDropdownFields()

        return binding.root
    }

    private fun setupDropdownFields() {
        // Definir as opções de raça e classe
        val races = listOf("Humano", "Elfo", "Anão")
        val classes = listOf("Guerreiro", "Paladino", "Ladrão")

        // Criar adaptadores para os campos de AutoComplete
        val adapterRaces = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, races)
        val adapterClasses = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, classes)

        // Associar os adaptadores aos AutoCompleteTextView
        binding.characterRace.setAdapter(adapterRaces)
        binding.characterClass.setAdapter(adapterClasses)

        // Ocultar o hint ao selecionar um item
        binding.characterRace.setOnItemClickListener { _, _, _, _ ->
            binding.characterRaceLayout.hint = null
        }
        binding.characterClass.setOnItemClickListener { _, _, _, _ ->
            binding.characterClassLayout.hint = null
        }
        // Forçar exibição da lista ao clicar
        binding.characterRace.setOnClickListener {
            binding.characterRace.showDropDown()
        }
        binding.characterClass.setOnClickListener {
            binding.characterClass.showDropDown()
        }
    }

    private fun loadCharacterData(characterId: Int) {
        val character = dbHelper.getCharacterById(characterId)
        character?.let {
            binding.characterName.setText(it.name)
            binding.characterRace.setText(it.race)
            binding.characterStrength.setText(it.strength.toString())
            binding.characterDexterity.setText(it.dexterity.toString())
            binding.characterConstitution.setText(it.constitution.toString())
            binding.characterIntelligence.setText(it.intelligence.toString())
            binding.characterWisdom.setText(it.wisdom.toString())
            binding.characterCharisma.setText(it.charisma.toString())
            binding.characterNotes.setText(it.notes)
            binding.characterClass.setText(it.classe)
            binding.characterLife.setText(it.life.toString())
            binding.characterMana.setText(it.mana.toString())
            binding.characterLvl.setText(it.lvl.toString())
            binding.characterArmor.setText(it.armor.toString())
            binding.characterOverlife.setText(it.overlife.toString())
            binding.characterMaxLife.setText(it.maxLife.toString())
            binding.characterMaxMana.setText(it.maxMana.toString())

            // Carregar habilidades após carregar o personagem
            setupAbilitiesSection()
        }
    }

    private fun editCharacter() {
        val name = binding.characterName.text.toString()
        val race = binding.characterRace.text.toString()
        val classe = binding.characterClass.text.toString()
        val notes = binding.characterNotes.text.toString()
        val strength = binding.characterStrength.text.toString().toIntOrNull() ?: 0
        val dexterity = binding.characterDexterity.text.toString().toIntOrNull() ?: 0
        val constitution = binding.characterConstitution.text.toString().toIntOrNull() ?: 0
        val intelligence = binding.characterIntelligence.text.toString().toIntOrNull() ?: 0
        val wisdom = binding.characterWisdom.text.toString().toIntOrNull() ?: 0
        val charisma = binding.characterCharisma.text.toString().toIntOrNull() ?: 0
        val life = binding.characterLife.text.toString().toIntOrNull() ?: 0
        val mana = binding.characterMana.text.toString().toIntOrNull() ?: 0
        val overlife = binding.characterOverlife.text.toString().toIntOrNull() ?: 0
        val armor = binding.characterArmor.text.toString().toIntOrNull() ?: 0
        val maxLife = binding.characterMaxLife.text.toString().toIntOrNull() ?: 0
        val maxMana = binding.characterMaxMana.text.toString().toIntOrNull() ?: 0
        val lvl = binding.characterLvl.text.toString().toIntOrNull() ?: 0

        if (name.isBlank() || race.isBlank()) {
            Toast.makeText(requireContext(), "Preencha o nome e a raça", Toast.LENGTH_SHORT).show()
            return
        }

        // Atualizar o personagem existente com a imagem selecionada
        val result = dbHelper.updateCharacter(
            characterId, name, race, classe, notes,
            strength, dexterity, constitution,
            intelligence, wisdom, charisma, life,
            mana, overlife, armor, maxLife, maxMana, lvl
            //, selectedImage
        )

        if (result != -1) {
            Toast.makeText(requireContext(), "Personagem atualizado com sucesso!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Erro ao atualizar personagem", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupAbilitiesSection() {
        var isExpanded = false

        binding.abilitiesHeader.bringToFront()

        binding.abilitiesHeader.setOnClickListener {
            isExpanded = !isExpanded
            binding.expandIcon.rotation = if (isExpanded) 180f else 0f
            binding.abilitiesContainer.visibility = if (isExpanded) View.VISIBLE else View.GONE

            if (isExpanded) {
                // Carregar habilidades ao expandir a seção
                loadAbilities()
            }
        }

        // Configurar o LayoutManager da RecyclerView
        binding.abilitiesList.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar o Adapter
        abilitiesAdapter = AbilitiesAdapter(mutableListOf(), onEdit = { ability ->
            dbHelper.updateAbility(ability)
            Toast.makeText(requireContext(), "Habilidade editada com sucesso!", Toast.LENGTH_SHORT).show()
        }, onDelete = { ability ->
            dbHelper.deleteAbility(ability.id)
            loadAbilities()
            Toast.makeText(requireContext(), "Habilidade excluída!", Toast.LENGTH_SHORT).show()
        })

        binding.addAbilityButton.setOnClickListener {
            showAddAbilityDialog()
        }

        // Configurar o Adapter na RecyclerView
        binding.abilitiesList.adapter = abilitiesAdapter
    }

    @SuppressLint("LongLogTag")
    private fun loadAbilities() {
        // Obter habilidades do personagem
        val abilities = dbHelper.getAbilitiesByCharacterId(characterId).toMutableList()

        // Atualizar o Adapter com as habilidades carregadas
        abilitiesAdapter.updateAbilities(abilities)
        Log.d("CharacterFragmentVisualize", "Habilidades carregadas: ${abilities.size}")
    }

    private fun showEditAbilityDialog(ability: Ability) {
        // Inflar o layout do dialog
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_ability, null)
        val abilityTitle = dialogView.findViewById<EditText>(R.id.abilityTitle)
        val abilityDescription = dialogView.findViewById<EditText>(R.id.abilityDescription)
        val buttonUpdateAbility = dialogView.findViewById<Button>(R.id.buttonAddAbility)

        // Preencher os campos com os dados da habilidade
        abilityTitle.setText(ability.title)
        abilityDescription.setText(ability.description)

        // Criar o Dialog
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Configurar o botão "Atualizar Habilidade"
        buttonUpdateAbility.text = "Atualizar"
        buttonUpdateAbility.setOnClickListener {
            val title = abilityTitle.text.toString().trim()
            val description = abilityDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "O título não pode estar vazio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Atualizar a habilidade no banco de dados
            ability.title = title
            ability.description = description
            dbHelper.updateAbility(ability)

            // Atualizar o adaptador e fechar o diálogo
            abilitiesAdapter.updateAbilities(dbHelper.getAbilitiesByCharacterId(characterId))
            Toast.makeText(requireContext(), "Habilidade atualizada com sucesso!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Exibir o Dialog
        dialog.show()
    }

    private fun showAddAbilityDialog() {
        // Inflar o layout do dialog
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_ability, null)
        val abilityTitle = dialogView.findViewById<EditText>(R.id.abilityTitle)
        val abilityDescription = dialogView.findViewById<EditText>(R.id.abilityDescription)
        val buttonAddAbility = dialogView.findViewById<Button>(R.id.buttonAddAbility)

        // Criar o Dialog
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Configurar o botão "Adicionar Habilidade"
        buttonAddAbility.setOnClickListener {
            val title = abilityTitle.text.toString().trim()
            val description = abilityDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "O título não pode estar vazio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Adicionar nova habilidade ao banco de dados
            val newAbility = Ability(0, title, description, characterId)
            dbHelper.addAbility(characterId, newAbility)

            // Atualizar a lista de habilidades e fechar o dialog
            (binding.abilitiesList.adapter as AbilitiesAdapter).updateAbilities(
                dbHelper.getAbilitiesByCharacterId(characterId)
            )
            Toast.makeText(requireContext(), "Habilidade adicionada com sucesso!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Exibir o Dialog
        dialog.show()
    }

    private fun addNewAbility() {
        val newAbility = Ability(0, "", "", characterId)
        dbHelper.addAbility(characterId, newAbility)
        val abilities = dbHelper.getAbilitiesByCharacterId(characterId).toMutableList()
        abilitiesAdapter.updateAbilities(abilities)
        Toast.makeText(requireContext(), "Nova habilidade adicionada!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}