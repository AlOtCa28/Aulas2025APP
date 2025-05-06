package com.example.aulas2025app.ENCARGADO.EngargadoYProfesores

import Adaptadores.AdaptadorAulas
import Adaptadores.AdaptadorProfesoresYEncargados
import Modelos.Aulas.Aula
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.FragmentEncargadoYProfesorBinding

class EncargadoYProfesorFragment : Fragment() {
    private var _binding: FragmentEncargadoYProfesorBinding? = null
    private val binding get() = _binding!!
    private val encargadoYProfesorViewModel: EncargadoYProfesorViewModel by viewModels()
    private lateinit var adaptadorRV: AdaptadorProfesoresYEncargados
    private var datosRepresentar: ArrayList<Aula> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEncargadoYProfesorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        encargadoYProfesorViewModel.getAulasVM()

        encargadoYProfesorViewModel.myResponseList.observe(viewLifecycleOwner) { responseList ->
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
        adaptadorRV = AdaptadorProfesoresYEncargados(requireContext(), datosRepresentar)
        binding.rvAulasEP.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAulasEP.adapter = adaptadorRV
    }
}