// CharacterListFragment.kt
package com.example.myrpgcharacter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.adapter.CharacterAdapter
import com.example.myrpgcharacter.databaseHelper.DatabaseHelper
import com.example.myrpgcharacter.databinding.FragmentCharacterListBinding
import com.example.myrpgcharacter.model.Character

class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Carregar personagens e configurar RecyclerView
        loadCharacters()

        // Configurar o botão de retorno para o Menu
        binding.buttonReturnToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_characterListFragment_to_menuFragment)
        }

        return binding.root
    }

    private fun loadCharacters() {
        val characters = dbHelper.getAllCharacters()
        adapter = CharacterAdapter(
            characters,
            onEditClick = { character ->
                val action = CharacterListFragmentDirections.
                    actionCharacterListFragmentToCharacterImageFragment(character.id)
                    //.action_characterListFragment_to_characterImageFragment(character.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { character ->
                deleteCharacter(character)
            }
        )

        binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCharacters.adapter = adapter
    }

    private fun deleteCharacter(character: Character) {
        dbHelper.deleteCharacterById(character.id)
        Toast.makeText(requireContext(), "Personagem deletado", Toast.LENGTH_SHORT).show()
        loadCharacters() // Atualizar a lista após exclusão
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}