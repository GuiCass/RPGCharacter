package com.example.myrpgcharacter.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myrpgcharacter.R
import com.example.myrpgcharacter.api.TranslationApi
import com.example.myrpgcharacter.databinding.FragmentTranslationBinding
import com.example.myrpgcharacter.translation.TranslationViewModel
import com.example.myrpgcharacter.translation.TranslationViewModelFactory

class TranslationFragment : Fragment() {

    private var _binding: FragmentTranslationBinding? = null
    private val binding get() = _binding!!

    // Initialize the ViewModel with the factory
    private val viewModel: TranslationViewModel by viewModels {
        TranslationViewModelFactory(TranslationApi.create())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslationBinding.inflate(inflater, container, false)

        setupLanguagesDropdown()

        // Set up button click to start translation
        binding.buttonTranslate.setOnClickListener {
            val textToTranslate = binding.inputText.text.toString()
            val selectedLanguage = binding.languages.text.toString().lowercase()
            if (selectedLanguage.isBlank() || textToTranslate.isBlank()) {
                Toast.makeText(requireContext(), "Preencha o o texto e a lingua", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.translateText(selectedLanguage, textToTranslate)
            }
        }

        // Configurar o botão de retorno
        binding.buttonReturn.setOnClickListener {
            requireActivity().onBackPressed()  // Voltar para a tela anterior
        }

        // Observe the translatedText LiveData
        viewModel.translatedText.observe(viewLifecycleOwner) { translatedText ->
            binding.translatedTextView.text = translatedText
        }

        return binding.root
    }

    private fun setupLanguagesDropdown() {
        // Define language options
        val languages = listOf("Cindarin", "Yoda", "Gungan")

        // Create an ArrayAdapter for the AutoCompleteTextView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, languages)

        // Associate the adapter with the AutoCompleteTextView
        binding.languages.setAdapter(adapter)

        // Hide the hint when an item is selected
        binding.languages.setOnItemClickListener { _, _, _, _ ->
            binding.languagesLayout.hint = null // Remove the hint when an item is selected
        }

        // Re-display the hint if the field becomes empty
        binding.languages.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.languagesLayout.hint = getString(R.string.race)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Force dropdown list to show on click
        binding.languages.setOnClickListener {
            binding.languages.showDropDown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}