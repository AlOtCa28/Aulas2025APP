package com.example.aulas2025app.JEFE.JefeDispositivos.DetalleDispositivo

import Modelos.Aulas.Aula
import Modelos.Dispositivo.Dispositivo
import android.R
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.aulas2025app.JEFE.JefeAulas.FragmentoAulasViewModel
import com.example.aulas2025app.databinding.ActivityDetalleDispositivoBinding

class DetalleDispositivoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleDispositivoBinding
    private val viewModel: DetalleDispositivoViewModel by viewModels()
    private val aulasViewModel: FragmentoAulasViewModel by viewModels()

    private val estados = listOf("Alta", "Baja", "Reparación")

    private var dispositivoId: Long = -1L
    private var aulaIdAsignada: Int? = null
    private var listaAulas = emptyList<Aula>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleDispositivoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Control de rol: solo consulta si rol = 3
        val esProfesor = Parametros.Parametros.rolUsuarioLogeado == 3

        // Configurar spinner estado
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)
        binding.spinnerEstado.adapter = adapter

        // Botón cancelar para volver atrás
        binding.btnCancelar.setOnClickListener {
            finish()
        }

        dispositivoId = intent.getLongExtra("ID_DISPOSITIVO", -1L)
        if (dispositivoId != -1L) {
            viewModel.cargarDispositivo(dispositivoId)
            aulasViewModel.getAulasVM()
        } else {
            Toast.makeText(this, "ID de dispositivo inválido", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Observar el dispositivo
        viewModel.dispositivo.observe(this) { disp ->
            if (disp != null) {
                binding.etCodigo.setText(disp.codigo)
                binding.etDescripcion.setText(disp.descripcion)
                binding.spinnerEstado.setSelection(estados.indexOf(disp.estado))
                binding.etMarca.setText(disp.marca)
                binding.etModelo.setText(disp.modelo)
                binding.etNumSerie.setText(disp.numeroSerie)
                binding.etUbicacionReferencia.setText(disp.ubicacionReferencia)
                binding.etUbicacionActual.setText(disp.ubicacionActual)
                binding.etTipo.setText(disp.tipo)

                aulaIdAsignada = disp.aulaId

                // Si ya tienes las aulas cargadas, selecciona la correcta
                actualizarSpinnerAulas()
            }
        }

        // Observar aulas
        aulasViewModel.myResponseList.observe(this) { aulas ->
            listaAulas = aulas
            actualizarSpinnerAulas()
        }

        viewModel.mensajeError.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }

        // **DESHABILITAR CAMPOS Y BOTÓN GUARDAR SI ES PROFESOR (solo lectura)**
        if (esProfesor) {
            binding.etCodigo.isEnabled = false
            binding.etDescripcion.isEnabled = false
            binding.spinnerEstado.isEnabled = false
            binding.etMarca.isEnabled = false
            binding.etModelo.isEnabled = false
            binding.etNumSerie.isEnabled = false
            binding.spinnerAulaAsignada.isEnabled = false
            binding.etTipo.isEnabled = false
            binding.etUbicacionReferencia.isEnabled = false
            binding.etUbicacionActual.isEnabled = false

            binding.btnGuardar.visibility = View.GONE
        } else {
            binding.btnGuardar.visibility = View.VISIBLE
        }

        // Guardar cambios, solo si tiene permiso
        binding.btnGuardar.setOnClickListener {
            if (esProfesor) {
                Toast.makeText(this, "No tienes permiso para editar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val aulaSeleccionadaNombre = binding.spinnerAulaAsignada.text.toString()
            val aulaSeleccionadaId = listaAulas.find { it.nombreAula == aulaSeleccionadaNombre }?.idAula
            aulaIdAsignada = aulaSeleccionadaId ?: aulaIdAsignada // conservar si null

            val dispEditado = Dispositivo(
                id = dispositivoId.toInt(),
                codigo = binding.etCodigo.text.toString(),
                descripcion = binding.etDescripcion.text.toString(),
                estado = binding.spinnerEstado.selectedItem.toString(),
                marca = binding.etMarca.text.toString(),
                modelo = binding.etModelo.text.toString(),
                numeroSerie = binding.etNumSerie.text.toString(),
                aulaId = aulaIdAsignada ?: 0,
                tipo = binding.etTipo.text.toString(),
                ubicacionReferencia = binding.etUbicacionReferencia.text.toString(),
                ubicacionActual = binding.etUbicacionActual.text.toString()
            )

            viewModel.guardarDispositivo(dispEditado) { success ->
                if (success) {
                    Toast.makeText(this, "Dispositivo actualizado", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar dispositivo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun actualizarSpinnerAulas() {
        if (listaAulas.isEmpty() || aulaIdAsignada == null) return

        val nombresAulas = listaAulas.map { it.nombreAula }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nombresAulas)
        binding.spinnerAulaAsignada.setAdapter(adapter)

        val aulaActual = listaAulas.find { it.idAula == aulaIdAsignada }
        aulaActual?.let {
            binding.spinnerAulaAsignada.setText(it.nombreAula, false)
        }

        binding.spinnerAulaAsignada.setOnItemClickListener { _, _, position, _ ->
            aulaIdAsignada = listaAulas[position].idAula
        }
    }
}
