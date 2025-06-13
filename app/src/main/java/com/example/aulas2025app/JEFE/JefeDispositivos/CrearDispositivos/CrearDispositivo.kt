package com.example.aulas2025app.JEFE.JefeDispositivos.CrearDispositivos

import Modelos.Aulas.Aula
import Modelos.Dispositivo.Dispositivo
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.JEFE.JefeAulas.FragmentoAulasViewModel
import com.example.aulas2025app.JEFE.JefeDispositivos.DispositivosViewModel
import com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionImpresora.EspecificacionImpresoraActivity
import com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionMonitor.EspecificacionMonitorActivity
import com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionPC.EspecificacionPcActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityCrearDispositivoBinding

class CrearDispositivo : AppCompatActivity() {

    private lateinit var binding: ActivityCrearDispositivoBinding
    private val dispositivosViewModel: DispositivosViewModel by viewModels()
    private val aulasViewModel: FragmentoAulasViewModel by viewModels()

    private var listaAulas: List<Aula> = emptyList()

    // Variables para usar en la redirección
    private var tipoSeleccionado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearDispositivoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar aulas en los tres spinners
        aulasViewModel.getAulasVM()

        aulasViewModel.myResponseList.observe(this) { aulas ->
            listaAulas = aulas
            val nombres = aulas.map { it.nombreAula }

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
            binding.spinnerAulas.adapter = adapter
            binding.spinnerUbicacionReferencia.adapter = adapter
            binding.spinnerUbicacionActual.adapter = adapter
        }

        binding.btnAddDispositivo.setOnClickListener {
            val codigo = binding.edtCodigo.text.toString().trim()
            val descripcion = binding.edtDescripcion.text.toString().trim()
            val estado = binding.edtEstado.text.toString().trim()
            val marca = binding.edtMarca.text.toString().trim()
            val modelo = binding.edtModelo.text.toString().trim()
            val numeroSerie = binding.edtNumeroSerie.text.toString().trim()
            val tipo = binding.edtTipo.text.toString().trim()

            tipoSeleccionado = tipo

            if (codigo.isEmpty() || descripcion.isEmpty() || estado.isEmpty() ||
                marca.isEmpty() || modelo.isEmpty() || numeroSerie.isEmpty() || tipo.isEmpty()
            ) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombreAula = binding.spinnerAulas.selectedItem.toString()
            val ubicacionRef = binding.spinnerUbicacionReferencia.selectedItem.toString()
            val ubicacionActual = binding.spinnerUbicacionActual.selectedItem.toString()

            val aulaSeleccionada = listaAulas.firstOrNull { it.nombreAula == nombreAula }
            if (aulaSeleccionada == null) {
                Toast.makeText(this, "Selecciona un aula válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoDispositivo = Dispositivo(
                id = null,
                codigo = codigo,
                descripcion = descripcion,
                estado = estado,
                marca = marca,
                modelo = modelo,
                numeroSerie = numeroSerie,
                ubicacionReferencia = ubicacionRef,
                ubicacionActual = ubicacionActual,
                aulaId = aulaSeleccionada.idAula!!,
                tipo = tipo
            )

            dispositivosViewModel.crearDispositivo(nuevoDispositivo)
        }


        dispositivosViewModel.resultado.observe(this) { exito ->
            if (!exito) {
                Toast.makeText(this, "Error al crear el dispositivo", Toast.LENGTH_SHORT).show()
            }
        }

        dispositivosViewModel.nuevoDispositivoId.observe(this) { id ->
            if (id != null) {
                when (tipoSeleccionado.lowercase()) {
                    "pc" -> {
                        val intent = Intent(this, EspecificacionPcActivity::class.java)
                        intent.putExtra("dispositivoId", id)
                        startActivity(intent)
                    }
                    "monitor" -> {
                        val intent = Intent(this, EspecificacionMonitorActivity::class.java)
                        intent.putExtra("dispositivoId", id)
                        startActivity(intent)
                    }
                    "impresora" -> {
                        val intent = Intent(this, EspecificacionImpresoraActivity::class.java)
                        intent.putExtra("dispositivoId", id)
                        startActivity(intent)
                    }
                    else -> {
                        // Opcional: manejar otros tipos o mostrar mensaje
                        Toast.makeText(this, "Tipo no soportado o no es el correcto: $tipoSeleccionado", Toast.LENGTH_SHORT).show()
                    }
                }
                finish()
            }
        }


        binding.btnCancelarDispositivo.setOnClickListener {
            finish()
        }
    }
}

