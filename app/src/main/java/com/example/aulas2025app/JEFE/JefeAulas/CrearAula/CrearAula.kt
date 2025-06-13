package com.example.aulas2025app.JEFE.JefeAulas.CrearAula

import Modelo.Usuario.Usuario
import Modelos.Aulas.Aula
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.aulas2025app.JEFE.JefeAulas.FragmentoAulasViewModel
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityCrearAulaBinding

class CrearAula : AppCompatActivity() {

    private lateinit var binding: ActivityCrearAulaBinding
    private val aulaViewModel: FragmentoAulasViewModel by viewModels()
    private val profesoresViewModel: ProfesoresViewModel by viewModels()

    private var listaEncargados: List<Usuario> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearAulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar encargados al spinner
        profesoresViewModel.getUsuariosVM()

        profesoresViewModel.myResponseList.observe(this) { usuarios ->
            listaEncargados = usuarios.filter { it.rol == 2 }
            val nombres = listaEncargados.map { it.nombre }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
            binding.spinnerEncargados.adapter = adapter
        }

        binding.btnAddAula.setOnClickListener {
            val nombre = binding.edtNombreAula.text.toString().trim()
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Introduce un nombre para el aula", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombreSeleccionado = binding.spinnerEncargados.selectedItem?.toString()
            val encargadoSeleccionado = listaEncargados.firstOrNull { it.nombre == nombreSeleccionado }

            if (encargadoSeleccionado == null) {
                Toast.makeText(this, "Selecciona un encargado válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevaAula = Aula(
                idAula = null,
                nombreAula = nombre,
                idEncargado = encargadoSeleccionado.id!!
            )

            aulaViewModel.crearAula(nuevaAula)
        }

        aulaViewModel.resultado.observe(this, Observer { exito ->
            if (exito) {
                Toast.makeText(this, "Aula creada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al crear el aula", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnCancelarAula.setOnClickListener {
            finish()
        }
    }
}