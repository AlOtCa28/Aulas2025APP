package com.example.aulas2025app.JEFE.JefeProfesores.CrearUsuario

import Modelo.Usuario.Usuario
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import com.example.aulas2025app.JEFE.JefeProfesores.ProfesoresViewModel
import com.example.aulas2025app.PROFESOR.ProfesorViewModel
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityCrearUsuarioBinding

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
            val password = binding.edtContraseANueva.text.toString()
            val rol = binding.edtRol.text.toString().toInt()


            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            } else {
                val usuario = Usuario(
                    nombre = nombre,
                    email = email,
                    contraseña = password,
                    rol = rol
                )

                profesorViewModel.registrarUsuarioVM(usuario)
                finish()
            }
        }

        binding.btnCancelarUsuario.setOnClickListener {
            finish()
        }
    }
}