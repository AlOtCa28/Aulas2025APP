package com.example.aulas2025app.PROFESOR

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.ENCARGADO.EngargadoYProfesores.EncargadoYProfesorFragment
import com.example.aulas2025app.LOGIN.LoginActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityProfesorBinding

class ProfesorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfesorBinding
    private lateinit var profesorViewModel: ProfesorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfesorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mtbMenu)

        setSupportActionBar(binding.mtbMenu)


        supportActionBar?.title = "PROFESORES"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EncargadoYProfesorFragment())
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }
}