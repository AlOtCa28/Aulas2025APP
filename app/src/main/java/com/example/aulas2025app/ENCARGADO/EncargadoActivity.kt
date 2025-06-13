package com.example.aulas2025app.ENCARGADO

import Adaptadores.AdaptadorAulas
import Adaptadores.AdaptadorProfesoresYEncargados
import Modelos.Aulas.Aula
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas2025app.JEFE.JefeAulas.FragmentoAulas
import com.example.aulas2025app.JEFE.JefeDispositivos.FragmentoDispositivos
import com.example.aulas2025app.LOGIN.LoginActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityEncargadoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class EncargadoActivity : AppCompatActivity() {

    private val fragmentAulas = FragmentoAulas.newInstance(false)
    private val fragmentDispositivos = FragmentoDispositivos.newInstance(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encargado)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_encargado)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Encargados"

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_encargado)

        // Mostrar el fragmento Aulas por defecto
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragments, fragmentAulas)
            .commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_aulas -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragments, fragmentAulas)
                        .commit()
                    true
                }
                R.id.menu_dispositivos -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragments, fragmentDispositivos)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


