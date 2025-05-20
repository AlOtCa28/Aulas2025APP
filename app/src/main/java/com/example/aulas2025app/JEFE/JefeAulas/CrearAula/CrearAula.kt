package com.example.aulas2025app.JEFE.JefeAulas.CrearAula

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.JEFE.JefeAulas.FragmentoAulasViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityCrearAulaBinding

class CrearAula : AppCompatActivity() {
    private lateinit var binding: ActivityCrearAulaBinding
    private val aulaViewModel: FragmentoAulasViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCrearAulaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAddAula.setOnClickListener {
            Toast.makeText(this, "Aula creada NO FUNCIONA", Toast.LENGTH_SHORT).show()
        }


        binding.btnCancelarAula.setOnClickListener {
            finish()
        }
    }
}