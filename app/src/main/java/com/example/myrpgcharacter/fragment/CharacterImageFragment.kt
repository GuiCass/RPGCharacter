// CharacterImageFragment.kt
package com.example.myrpgcharacter.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.databaseHelper.DatabaseHelper
import com.example.myrpgcharacter.databinding.FragmentCharacterImageBinding

class CharacterImageFragment : Fragment() {
    private var _binding: FragmentCharacterImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private var characterId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterImageBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Obter o ID do personagem passado
        characterId = arguments?.getInt("characterId") ?: -1
        if (characterId != -1) loadCharacterData(characterId)

        // Configurar botão para acessar detalhes
        binding.arrowRight.setOnClickListener {
            val action = CharacterImageFragmentDirections.actionCharacterImageFragmentToCharacterFragmentVisualize(characterId)
            findNavController().navigate(action)
        }

        // Configurar botão de retorno
        binding.buttonReturn.setOnClickListener {
            findNavController().navigate(R.id.action_characterImageFragment_to_characterListFragment)
        }

        return binding.root
    }

    private fun loadCharacterData(characterId: Int) {
        val character = dbHelper.getCharacterById(characterId)
        character?.let {
            // Configurar nome do personagem
            binding.characterName.text = it.name

            // Configurar imagem do personagem
            it.image?.let { imageBytes ->
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.characterImage.setImageBitmap(bitmap)
            } ?: run {
                binding.characterImage.setImageResource(R.drawable.sem_imagem)
            }

            // Configurar barra de vida e texto
            val maxLife = it.maxLife
            val currentLife = it.life
            binding.lifeBar.max = maxLife
            binding.lifeBar.progress = currentLife
            val overlife = it.overlife
            binding.lifeMiniBar.max = overlife
            binding.lifeMiniBar.progress = overlife
            val maxMana = it.maxMana
            val currentMana = it.mana
            binding.manaBar.max = maxMana
            binding.manaBar.progress = currentMana
            val armor = it.armor
            binding.armorBar.max = armor
            binding.armorBar.progress = armor

            // Atualizar o texto centralizado da barra de vida
            binding.lifeText.text = "$currentLife/$maxLife"
            binding.lifeMiniText.text = "$overlife"
            binding.manaText.text = "$currentMana/$maxMana"
            binding.armorText.text = "$armor"
            binding.lvlText.text = "${it.lvl}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}