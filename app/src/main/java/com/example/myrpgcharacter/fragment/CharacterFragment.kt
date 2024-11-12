package com.example.myrpgcharacter.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.databaseHelper.DatabaseHelper
import com.example.myrpgcharacter.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    // Instância do DatabaseHelper
    private lateinit var dbHelper: DatabaseHelper
    private var selectedImage: ByteArray? = null // Armazena a imagem selecionada

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)

        // Inicialize o DatabaseHelper
        dbHelper = DatabaseHelper(requireContext())

        // Configurar os campos de seleção para "Raça" e "Classe"
        setupDropdownFields()

        // Configurar listener de perda de foco para formatação do campo de nome
        setupNameFieldFocusListener()

        // Configurar botão de seleção de imagem
        binding.buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Configurar o botão de salvar
        binding.buttonSave.setOnClickListener {
            saveCharacter()
        }

        // Configurar o botão de retorno
        binding.buttonReturn.setOnClickListener {
            requireActivity().onBackPressed()  // Voltar para a tela anterior
        }

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

    private fun setupNameFieldFocusListener() {
        binding.characterName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val formattedText = binding.characterName.text.toString()
                    .replace("\n", "")
                    .replace("\\s+".toRegex(), " ")
                    .trim()
                binding.characterName.setText(formattedText)
            }
        }
    }

    private fun saveCharacter() {
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
        val life = 0
        val mana = 0
        val overlife = 0
        val armor = binding.characterArmor.text.toString().toIntOrNull() ?: 0
        val maxLife = binding.characterMaxLife.text.toString().toIntOrNull() ?: 0
        val maxMana = binding.characterMaxMana.text.toString().toIntOrNull() ?: 0
        val lvl = binding.characterLvl.text.toString().toIntOrNull() ?: 0

        if (name.isBlank()) {
            Toast.makeText(requireContext(), "Preencha o nome e a raça", Toast.LENGTH_SHORT).show()
            return
        }

        // Inserir os dados no banco de dados
        val result = dbHelper.addCharacter(
            name, race, classe, notes,
            strength, dexterity, constitution,
            intelligence, wisdom, charisma, selectedImage,
            life, mana, overlife, armor, maxLife, maxMana, lvl
        )

        if (result != -1L) {
            Toast.makeText(requireContext(), "Personagem salvo com sucesso!", Toast.LENGTH_LONG).show()
            clearFields()
        } else {
            Toast.makeText(requireContext(), "Erro ao salvar personagem", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.characterName.text.clear()
        binding.characterRace.text.clear()
        binding.characterClass.text.clear()
        binding.characterStrength.text.clear()
        binding.characterDexterity.text.clear()
        binding.characterConstitution.text.clear()
        binding.characterIntelligence.text.clear()
        binding.characterWisdom.text.clear()
        binding.characterCharisma.text.clear()
        binding.characterImage.setImageResource(R.drawable.sem_imagem)
        binding.characterLvl.text.clear()
        binding.characterArmor.text.clear()
        binding.characterMaxLife.text.clear()
        binding.characterMaxMana.text.clear()
        selectedImage = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                binding.characterImage.setImageURI(it)
                val inputStream = requireContext().contentResolver.openInputStream(it)
                selectedImage = inputStream?.readBytes()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
