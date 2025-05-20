package com.example.aulas2025app.JEFE.JefeProfesores.CrearUsuario

import Modelo.Usuario.Usuario
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.databinding.ActivityCrearUsuarioBinding
import com.google.gson.Gson

class CrearUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityCrearUsuarioBinding
    private val profesorViewModel: ProfesoresViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCrearUsuario.setOnClickListener {
            val nombre = binding.edtNombreNuevo.text.toString()
            val email = binding.edtEmailNuevo.text.toString()
            val passwordHash = binding.edtContraseANueva.text.toString()
            val rol = binding.edtRol.text.toString().toIntOrNull() ?: -1

            if (nombre.isBlank() || email.isBlank() || passwordHash.isBlank() || rol <= 0) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = Usuario(
                nombre = nombre,
                email = email,
                passwordHash = passwordHash,
                rol = rol
            )

            profesorViewModel.registrarUsuarioVM(usuario)
            finish()
        }


        binding.btnCancelarUsuario.setOnClickListener {
            finish()
        }
    }
}