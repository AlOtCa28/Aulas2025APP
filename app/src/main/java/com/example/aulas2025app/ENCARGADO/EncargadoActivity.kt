package com.example.aulas2025app.ENCARGADO

import Adaptadores.AdaptadorAulas
import Adaptadores.AdaptadorProfesoresYEncargados
import Modelos.Aulas.Aula
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.ENCARGADO.EngargadoYProfesores.EncargadoYProfesorFragment
import com.example.aulas2025app.LOGIN.LoginActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityEncargadoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class EncargadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncargadoBinding
    private lateinit var encargadoViewModel: EncargadoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEncargadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mtbMenu)

        setSupportActionBar(binding.mtbMenu)


        supportActionBar?.title = "ENCARGADOS"
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