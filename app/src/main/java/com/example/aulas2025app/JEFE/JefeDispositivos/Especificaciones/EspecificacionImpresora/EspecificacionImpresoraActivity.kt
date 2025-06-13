package com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionImpresora

import Modelos.EspecificacioensImpresora.EspecificacionImpresora
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityEspecificacionImpresoraBinding

class EspecificacionImpresoraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEspecificacionImpresoraBinding
    private val impresoraViewModel: EspecificacionImpresoraViewModel by viewModels()

    private var dispositivoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEspecificacionImpresoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dispositivoId = intent.getIntExtra("dispositivoId", -1)

        binding.btnGuardarImpresora.setOnClickListener {
            val tipo = binding.edtTipoImpresora.text.toString().trim()

            if (tipo.isEmpty()) {
                Toast.makeText(this, "Introduce el tipo de impresora", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val espec = EspecificacionImpresora(
                dispositivoId = dispositivoId,
                tipo = tipo
            )

            impresoraViewModel.crearEspecificacionImpresora(espec)
        }

        impresoraViewModel.resultado.observe(this) { exito ->
            if (exito) {
                Toast.makeText(this, "Especificación guardada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar especificación", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
