package com.example.aulas2025app.JEFE.JefeAulas.DetalleAula

import API.UserNetwork
import Adaptadores.DispositivoAdapter
import Adaptadores.DispositivoDetalleAdapter
import Modelo.Usuario.Usuario
import Modelos.Aulas.Aula
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityDetalleAulaBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class DetalleAulaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleAulaBinding
    private lateinit var viewModel: DetalleAulaViewModel
    private lateinit var profesoresViewModel: ProfesoresViewModel
    private lateinit var adapterDispositivos: DispositivoDetalleAdapter

    private var listaEncargados: List<Usuario> = emptyList()
    private var aula: Aula? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleAulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val esProfesor = Parametros.Parametros.rolUsuarioLogeado == 3

        aula = intent.getSerializableExtra("aula") as? Aula

        viewModel = ViewModelProvider(this).get(DetalleAulaViewModel::class.java)
        profesoresViewModel = ViewModelProvider(this).get(ProfesoresViewModel::class.java)

        adapterDispositivos = DispositivoDetalleAdapter { dispositivo ->
            Toast.makeText(this, "Dispositivo: ${dispositivo.codigo}", Toast.LENGTH_SHORT).show()
        }

        binding.rvDispositivos.adapter = adapterDispositivos
        binding.rvDispositivos.layoutManager = LinearLayoutManager(this)

        profesoresViewModel.myResponseList.observe(this) { usuarios ->
            listaEncargados = usuarios.filter { it.rol == 2 }
            val nombresEncargados = listaEncargados.map { it.nombre }

            val adapterSpinner = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                nombresEncargados
            )
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerEncargado.adapter = adapterSpinner

            aula?.idEncargado?.let { idEncargadoActual ->
                val posSeleccionada = listaEncargados.indexOfFirst { it.id == idEncargadoActual }
                if (posSeleccionada >= 0) {
                    binding.spinnerEncargado.setSelection(posSeleccionada)
                }
            }

            if (esProfesor) {
                binding.spinnerEncargado.isEnabled = false
            }
        }

        profesoresViewModel.getUsuariosVM()

        aula?.let {
            binding.etNombreAula.setText(it.nombreAula)

            it.idAula?.let { idAula ->
                viewModel.dispositivos.observe(this) { dispositivos ->
                    if (dispositivos.isNotEmpty()) {
                        binding.tvDispositivosTitulo.visibility = View.VISIBLE
                        binding.rvDispositivos.visibility = View.VISIBLE
                        adapterDispositivos.submitList(dispositivos)
                    } else {
                        binding.tvDispositivosTitulo.visibility = View.GONE
                        binding.rvDispositivos.visibility = View.GONE
                        adapterDispositivos.submitList(emptyList())
                    }
                }
                viewModel.obtenerDispositivosPorAula(idAula)
            }
        }

        if (esProfesor) {
            binding.etNombreAula.isEnabled = false
            binding.btnGuardarAula.visibility = View.GONE
        } else {
            binding.etNombreAula.isEnabled = true
            binding.btnGuardarAula.visibility = View.VISIBLE
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnGuardarAula.setOnClickListener {
            val nombre = binding.etNombreAula.text.toString().trim()
            val posicionSeleccionada = binding.spinnerEncargado.selectedItemPosition
            val idEncargadoSeleccionado = if (posicionSeleccionada in listaEncargados.indices) {
                listaEncargados[posicionSeleccionada].id
            } else null

            if (nombre.isNotEmpty() && idEncargadoSeleccionado != null) {
                val aulaActualizada = Aula(
                    idAula = aula?.idAula,
                    nombreAula = nombre,
                    idEncargado = idEncargadoSeleccionado
                )
                viewModel.guardarAula(aulaActualizada) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Aula guardada correctamente", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this, "Error guardando aula", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Rellena todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
