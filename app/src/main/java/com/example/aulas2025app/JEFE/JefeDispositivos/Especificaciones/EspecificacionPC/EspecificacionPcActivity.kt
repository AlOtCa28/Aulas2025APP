package com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionPC

import Modelos.EspecificacionesPC.EspecificacionPC
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.aulas2025app.databinding.ActivityEspecificacionPcBinding


class EspecificacionPcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEspecificacionPcBinding
    private val viewModel: EspecificacionPCViewModel by viewModels()


    private var dispositivoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEspecificacionPcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar ID del dispositivo desde Intent
        dispositivoId = intent.getIntExtra("dispositivoId", -1)
        if (dispositivoId == -1) {
            Toast.makeText(this, "Error: ID de dispositivo no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.btnGuardar.setOnClickListener {
            val cpu = binding.edtCpu.text.toString().trim()
            val ram = binding.edtRam.text.toString().trim()
            val hd = binding.edtHd.text.toString().trim()
            val so = binding.edtSO.text.toString().trim()

            if (cpu.isEmpty() || ram.isEmpty() || hd.isEmpty() || so.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val especificacion = EspecificacionPC(
                dispositivoId = dispositivoId,
                cpu = cpu,
                ram = ram,
                hd = hd,
                sistemaOperativo = so
            )

            viewModel.crearEspecificacionPC(especificacion)
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        viewModel.resultado.observe(this) { exito ->
            if (exito) {
                Toast.makeText(this, "Especificación guardada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}