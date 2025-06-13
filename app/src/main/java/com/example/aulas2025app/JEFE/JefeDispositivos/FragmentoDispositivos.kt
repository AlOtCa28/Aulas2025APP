package com.example.aulas2025app.JEFE.JefeDispositivos

import Adaptadores.DispositivoAdapter
import Modelos.Dispositivo.Dispositivo
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.JEFE.JefeDispositivos.CrearDispositivos.CrearDispositivo
import com.example.aulas2025app.databinding.FragmentDispositivosBinding

class FragmentoDispositivos : Fragment() {
    private var _binding: FragmentDispositivosBinding? = null
    private val binding get() = _binding!!

    private val dispositivosViewModel: DispositivosViewModel by viewModels()
    private lateinit var adaptadorDispositivos: DispositivoAdapter
    private var listaDispositivos: ArrayList<Dispositivo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDispositivosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        dispositivosViewModel.getDispositivosVM()

        dispositivosViewModel.myResponseList.observe(viewLifecycleOwner) { response ->
            Log.d("FragmentoDispositivos", "Lista recibida en fragmento: $response")
            listaDispositivos.clear()
            listaDispositivos.addAll(response)
            adaptadorDispositivos.notifyDataSetChanged()
        }

        dispositivosViewModel.error.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), CrearDispositivo::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDialogoEliminar(dispositivo: Dispositivo) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar dispositivo")
            .setMessage("¿Seguro que quieres eliminar el dispositivo ${dispositivo.codigo}?")
            .setPositiveButton("Sí") { _, _ ->
                val idInt = dispositivo.id ?: -1
                if (idInt != -1) {
                    dispositivosViewModel.eliminarDispositivoYDependencias(idInt) { success ->
                        if (success) {
                            Toast.makeText(requireContext(), "Dispositivo eliminado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al eliminar dispositivo", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "ID de dispositivo inválido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setupRecyclerView() {
        adaptadorDispositivos = DispositivoAdapter(requireContext(), listaDispositivos) { dispositivo ->
            mostrarDialogoEliminar(dispositivo)
        }
        binding.rvDispositivos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDispositivos.adapter = adaptadorDispositivos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

