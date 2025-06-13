package com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionMonitor

import Modelos.EspecificacionesMonitor.EspecificacionMonitor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityEspecificacionMonitorBinding

class EspecificacionMonitorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEspecificacionMonitorBinding
    private val monitorViewModel: EspecificacionMonitorViewModel by viewModels()

    private var dispositivoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEspecificacionMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dispositivoId = intent.getIntExtra("dispositivoId", -1)

        binding.btnGuardarMonitor.setOnClickListener {
            val resolucion = binding.edtResolucion.text.toString().trim()
            val pulgadasTexto = binding.edtPulgadas.text.toString().trim()
            val orientable = binding.checkboxOrientable.isChecked

            if (resolucion.isEmpty() || pulgadasTexto.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pulgadas = pulgadasTexto.toDoubleOrNull()
            if (pulgadas == null) {
                Toast.makeText(this, "Introduce un valor válido para las pulgadas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val espec = EspecificacionMonitor(
                dispositivoId = dispositivoId,
                resolucionMaxima = resolucion,
                pulgadas = pulgadas,
                orientable = orientable
            )

            monitorViewModel.crearEspecificacionMonitor(espec)
        }

        monitorViewModel.resultado.observe(this) { exito ->
            if (exito) {
                Toast.makeText(this, "Especificación guardada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar especificación", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
