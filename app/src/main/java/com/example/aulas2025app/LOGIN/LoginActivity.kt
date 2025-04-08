package com.example.aulas2025app.LOGIN

import Modelo.Usuario.UsuarioLogIn
import Parametros.Parametros
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aulas2025app.ENCARGADO.EncargadoActivity
import com.example.aulas2025app.JEFE.JefeActivity
import com.example.aulas2025app.PROFESOR.ProfesorActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        loginViewModel.myResponse.observe(this, Observer { user ->
            user?.let {
                if (binding.edtNombre.text?.isEmpty() != false || binding.estContra.text?.isEmpty() != false) {
                    Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = if (it.rol == 1) {
                        Intent(this, JefeActivity::class.java)
                    } else if (it.rol == 2) {
                        Intent(this, EncargadoActivity::class.java)
                    } else {
                        Intent(this, ProfesorActivity::class.java)
                    }
                    startActivity(intent)
                    Parametros.usuarioLogeadoId = it.idUsuario
                    Parametros.usuarioLogeado = binding.edtNombre.text.toString()
                    limpiar()
                    loginViewModel.limpiarRespuesta()
                }
            }
        })

        binding.btnLogin.setOnClickListener {
            if (binding.edtNombre.text?.isEmpty() != false || binding.estContra.text?.isEmpty() != false) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.loginVM(UsuarioLogIn(binding.edtNombre.text.toString(),binding.estContra.text.toString()))
            }
        }


        loginViewModel.errorCode.observe(this) { errorCode ->
            if (errorCode != null) {
                when (errorCode) {
                    200 -> Toast.makeText(this, "Sesion Iniciada", Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(this,"Error 400: Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(this, "Error 404: El usuario no existe", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSalir.setOnClickListener {
            finish()
        }
    }

    private fun limpiar() {
        binding.edtNombre.text?.clear()
        binding.estContra.text?.clear()
    }
}