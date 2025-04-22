package com.example.aulas2025app.JEFE.JefeAulas

import Adaptadores.AdaptadorAulas
import Modelos.Aulas.Aula
import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.FragmentFragmentoAulasBinding

class FragmentoAulas : Fragment() {
    private var _binding: FragmentFragmentoAulasBinding? = null
    private val binding get() = _binding!!


    private val fragmentAulasViewModel: FragmentoAulasViewModel by viewModels()
    var datosRepresentar : ArrayList<Aula> = ArrayList()
    lateinit var adaptadorRV : AdaptadorAulas


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAulasBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        fragmentAulasViewModel.getAulasVM()

        fragmentAulasViewModel.myResponseList.observe(viewLifecycleOwner) { responseList ->
            datosRepresentar.clear()
            datosRepresentar.addAll(responseList)
            adaptadorRV.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adaptadorRV = AdaptadorAulas(requireContext(), datosRepresentar)
        binding.rvAulas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAulas.adapter = adaptadorRV
    }
}