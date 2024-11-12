package com.example.myrpgcharacter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        // Configurar os botões para navegar para as telas específicas
        binding.buttonAddCharacter.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_characterFragment)
        }

        binding.buttonViewCharacters.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_characterListFragment)
        }

        binding.buttonTranslate.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_translationFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
