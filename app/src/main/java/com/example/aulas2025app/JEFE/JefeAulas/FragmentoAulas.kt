package com.example.aulas2025app.JEFE.JefeAulas

import Adaptadores.AdaptadorAulas
import Modelo.Usuario.Usuario
import Modelos.Aulas.Aula
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.JEFE.JefeAulas.CrearAula.CrearAula
import com.example.aulas2025app.JEFE.JefeAulas.DetalleAula.DetalleAulaActivity
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.FragmentFragmentoAulasBinding

class FragmentoAulas : Fragment() {
    private var _binding: FragmentFragmentoAulasBinding? = null
    private val binding get() = _binding!!

    private val fragmentAulasViewModel: FragmentoAulasViewModel by viewModels()
    private val profesoresViewModel: ProfesoresViewModel by viewModels()

    private var datosRepresentar: ArrayList<Aula> = ArrayList()
    private lateinit var adaptadorRV: AdaptadorAulas
    private var listaProfesores: List<Usuario> = emptyList()

    private var esEditable: Boolean = true

    private lateinit var detalleAulaLauncher: ActivityResultLauncher<Intent> // Cambiado

    companion object {
        fun newInstance(esEditable: Boolean = true): FragmentoAulas {
            val fragment = FragmentoAulas()
            val args = Bundle()
            args.putBoolean("editable", esEditable)
            fragment.arguments = args
            return fragment
        }
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

        esEditable = arguments?.getBoolean("editable") ?: true
        binding.btnAddPrueba.visibility = if (esEditable) View.VISIBLE else View.GONE

        // ✅ REGISTRAR AQUÍ el launcher
        detalleAulaLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                fragmentAulasViewModel.getAulasVM()
            }
        }

        setupRecyclerView()

        profesoresViewModel.getUsuariosVM()
        profesoresViewModel.myResponseList.observe(viewLifecycleOwner) { lista ->
            listaProfesores = lista
            adaptadorRV.setProfesores(listaProfesores)
            adaptadorRV.notifyDataSetChanged()
        }

        fragmentAulasViewModel.getAulasVM()
        fragmentAulasViewModel.myResponseList.observe(viewLifecycleOwner) { responseList ->
            datosRepresentar.clear()
            datosRepresentar.addAll(responseList)
            adaptadorRV.notifyDataSetChanged()
        }

        fragmentAulasViewModel.resOperacion.observe(viewLifecycleOwner) { exito ->
            if (exito) {
                Toast.makeText(requireContext(), "Aula eliminada correctamente", Toast.LENGTH_SHORT).show()
                fragmentAulasViewModel.getAulasVM()
            } else {
                Toast.makeText(requireContext(), "Error al eliminar el aula", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAddPrueba.setOnClickListener {
            val intent = Intent(requireContext(), CrearAula::class.java)
            detalleAulaLauncher.launch(intent)
        }
    }

    private fun setupRecyclerView() {
        adaptadorRV = AdaptadorAulas(requireContext(), datosRepresentar)

        adaptadorRV.setOnClickListener { aula ->
            val intent = Intent(requireContext(), DetalleAulaActivity::class.java)
            intent.putExtra("aula", aula)
            detalleAulaLauncher.launch(intent)
        }

        if (esEditable) {
            adaptadorRV.setOnLongClickListener { aula ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar Aula")
                    .setMessage("¿Deseas eliminar el aula '${aula.nombreAula}'?")
                    .setPositiveButton("Sí") { _, _ ->
                        aula.idAula?.let {
                            fragmentAulasViewModel.eliminarAula(it)
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }

        binding.rvAulas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAulas.adapter = adaptadorRV
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

