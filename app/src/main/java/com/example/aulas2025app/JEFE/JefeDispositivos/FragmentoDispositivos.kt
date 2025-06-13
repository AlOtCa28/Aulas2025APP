package com.example.aulas2025app.JEFE.JefeDispositivos

import Adaptadores.DispositivoAdapter
import Modelos.Dispositivo.Dispositivo
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.JEFE.JefeDispositivos.CrearDispositivos.CrearDispositivo
import com.example.aulas2025app.JEFE.JefeDispositivos.DetalleDispositivo.DetalleDispositivoActivity
import com.example.aulas2025app.databinding.FragmentDispositivosBinding

class FragmentoDispositivos : Fragment() {

    private var _binding: FragmentDispositivosBinding? = null
    private val binding get() = _binding!!

    private val dispositivosViewModel: DispositivosViewModel by viewModels()
    private lateinit var adaptadorDispositivos: DispositivoAdapter
    private var listaDispositivos: ArrayList<Dispositivo> = arrayListOf()

    // Nueva propiedad para definir si es lectura o edición
    private var modoLectura: Boolean = false

    private val detalleDispositivoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            dispositivosViewModel.getDispositivosVM()
        }
    }

    // Nuevo launcher para crear dispositivo
    private val crearDispositivoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            dispositivosViewModel.getDispositivosVM()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Leer el argumento de modo lectura
        modoLectura = arguments?.getBoolean("modoLectura", false) ?: false
    }

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
            listaDispositivos.clear()
            listaDispositivos.addAll(response)
            adaptadorDispositivos.notifyDataSetChanged()
        }

        dispositivosViewModel.error.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Mostrar u ocultar el botón según el modo
        if (modoLectura) {
            binding.fabAdd.hide()
        } else {
            binding.fabAdd.setOnClickListener {
                val intent = Intent(requireContext(), CrearDispositivo::class.java)
                crearDispositivoLauncher.launch(intent)  // Usamos el launcher para recibir resultado
            }
        }
    }

    private fun mostrarDialogoEliminar(dispositivo: Dispositivo) {
        if (modoLectura) return // No permitir eliminar

        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar dispositivo")
            .setMessage("¿Seguro que quieres eliminar el dispositivo ${dispositivo.codigo}?")
            .setPositiveButton("Sí") { _, _ ->
                val idInt = dispositivo.id ?: -1
                if (idInt != -1) {
                    dispositivosViewModel.eliminarDispositivoYDependencias(idInt) { success ->
                        val mensaje = if (success) "Dispositivo eliminado" else "Error al eliminar dispositivo"
                        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setupRecyclerView() {
        adaptadorDispositivos = DispositivoAdapter(
            requireContext(),
            listaDispositivos,
            onItemClick = { dispositivo ->
                val intent = Intent(requireContext(), DetalleDispositivoActivity::class.java)
                intent.putExtra("ID_DISPOSITIVO", dispositivo.id?.toLong() ?: -1L)
                detalleDispositivoLauncher.launch(intent)
            },
            onItemLongClick = { dispositivo ->
                if (!modoLectura) {
                    mostrarDialogoEliminar(dispositivo)
                }
            }
        )
        binding.rvDispositivos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDispositivos.adapter = adaptadorDispositivos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(esSoloLectura: Boolean): FragmentoDispositivos {
            val fragment = FragmentoDispositivos()
            val args = Bundle()
            args.putBoolean("modoLectura", esSoloLectura)
            fragment.arguments = args
            return fragment
        }
    }
}
