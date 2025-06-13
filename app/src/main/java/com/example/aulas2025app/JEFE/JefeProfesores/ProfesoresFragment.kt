package com.example.aulas2025app.JEFE.JefeProfesores

import Adaptadores.MiAdaptadorRV
import Modelo.Usuario.Usuario
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.JEFE.JefeProfesores.CrearUsuario.CrearUsuario
import com.example.aulas2025app.JEFE.JefeProfesores.DetallesUsuario.DetalleUsuarioActivity
import com.example.aulas2025app.PROFESOR.ProfesorViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.FragmentProfesoresBinding

class ProfesoresFragment : Fragment() {
    private var _binding: FragmentProfesoresBinding? = null
    private val binding get() = _binding!!

    private val profesorViewModel: ProfesoresViewModel by viewModels()
    var datosRepresentar: ArrayList<Usuario> = ArrayList()
    lateinit var adaptadorRV: MiAdaptadorRV

    // Registro para recibir resultado desde DetalleUsuarioActivity
    private val detalleUsuarioLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Recarga la lista cuando volvemos con resultado OK
            profesorViewModel.getUsuariosVM()
        }
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
            datosRepresentar.addAll(usuarios.filter { it.rol != 1 })
            adaptadorRV.notifyDataSetChanged()
        }

        profesorViewModel.resultadoBorrar.observe(viewLifecycleOwner) { (exito, usuario) ->
            if (exito && usuario != null) {
                val index = datosRepresentar.indexOfFirst { it.id == usuario.id }
                if (index != -1) {
                    datosRepresentar.removeAt(index)
                    adaptadorRV.notifyItemRemoved(index)
                    Toast.makeText(requireContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAddUsuario.setOnClickListener {
            val intent = Intent(requireContext(), CrearUsuario::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvListaProfesores.layoutManager = linearLayoutManager
        adaptadorRV = MiAdaptadorRV(requireContext(), datosRepresentar,
            onBorrarClick = { usuario, posicion ->
                profesorViewModel.borrarUsuario(usuario)
            },
            onVerDetallesClick = { usuario ->
                val intent = Intent(requireContext(), DetalleUsuarioActivity::class.java)
                intent.putExtra("usuario", usuario)
                detalleUsuarioLauncher.launch(intent)
            }
        )
        binding.rvListaProfesores.adapter = adaptadorRV
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

