package com.example.aulas2025app.JEFE.JefeProfesores.DetallesUsuario

import Modelo.Usuario.Usuario
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityDetalleUsuarioBinding

class DetalleUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleUsuarioBinding
    private val viewModel: DetalleUsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getSerializableExtra("usuario") as? Usuario

        if (usuario != null) {
            viewModel.setUsuario(usuario)
        } else {
            Toast.makeText(this, "Usuario no proporcionado", Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.usuario.observe(this) { usuario ->
            if (usuario != null) {
                mostrarDatosUsuario(usuario)
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.btnGuardar.setOnClickListener {
            val usuarioActualizado = obtenerUsuarioDesdeCampos()
            if (validarUsuario(usuarioActualizado)) {
                viewModel.guardarUsuario(usuarioActualizado) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun mostrarDatosUsuario(usuario: Usuario) {
        binding.etNombre.setText(usuario.nombre)
        binding.etEmail.setText(usuario.email)
        binding.etPassword.setText(usuario.passwordHash)
        binding.etRol.setText(usuario.rol.toString())
    }

    private fun obtenerUsuarioDesdeCampos(): Usuario {
        val nombre = binding.etNombre.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val rol = binding.etRol.text.toString().toIntOrNull() ?: 0

        // Mantener id del usuario actual (si lo guardas en ViewModel podrías recuperar)
        val idActual = viewModel.usuario.value?.id

        return Usuario(
            id = idActual,
            nombre = nombre,
            email = email,
            passwordHash = password,
            rol = rol
        )
    }

    private fun validarUsuario(usuario: Usuario): Boolean {
        if (usuario.nombre.isEmpty()) {
            binding.tilNombre.error = "El nombre es obligatorio"
            return false
        } else {
            binding.tilNombre.error = null
        }

        if (usuario.email.isEmpty()) {
            binding.tilEmail.error = "El email es obligatorio"
            return false
        } else {
            binding.tilEmail.error = null
        }

        if (usuario.passwordHash.isEmpty()) {
            binding.tilPassword.error = "La contraseña es obligatoria"
            return false
        } else {
            binding.tilPassword.error = null
        }

        if (usuario.rol <= 0) {
            binding.tilRol.error = "El rol debe ser un número positivo"
            return false
        } else {
            binding.tilRol.error = null
        }

        return true
    }
}
