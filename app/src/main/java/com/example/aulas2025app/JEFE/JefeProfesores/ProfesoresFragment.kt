package com.example.aulas2025app.JEFE.JefeProfesores

import Adaptadores.MiAdaptadorRV
import Modelo.Usuario.Usuario
import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.JEFE.JefeProfesores.CrearUsuario.CrearUsuario
import com.example.aulas2025app.PROFESOR.ProfesorViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.FragmentProfesoresBinding

class ProfesoresFragment : Fragment() {
    private var _binding: FragmentProfesoresBinding? = null
    private val binding get() = _binding!!

    private val profesorViewModel: ProfesoresViewModel by viewModels()
    var datosRepresentar : ArrayList<Usuario> = ArrayList()
    lateinit var adaptadorRV : MiAdaptadorRV


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfesoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()


        profesorViewModel.getUsuariosVM()

        profesorViewModel.myResponseList.observe(viewLifecycleOwner) { usuarios ->
            datosRepresentar.clear()
            datosRepresentar.addAll(usuarios.filter { it.rol != 1})
            adaptadorRV.notifyDataSetChanged()

        }

        binding.btnAddUsuario.setOnClickListener {
            val intent = Intent(requireContext(), CrearUsuario::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvListaProfesores.layoutManager = linearLayoutManager
        adaptadorRV = MiAdaptadorRV(requireContext(), datosRepresentar)
        binding.rvListaProfesores.adapter = adaptadorRV
    }
}