package com.example.aulas2025app.PROFESOR

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulas2025app.JEFE.JefeAulas.FragmentoAulas
import com.example.aulas2025app.JEFE.JefeDispositivos.FragmentoDispositivos
import com.example.aulas2025app.LOGIN.LoginActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityProfesorBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfesorActivity : AppCompatActivity() {

    private val fragmentAulas = FragmentoAulas.newInstance(false)
    private val fragmentDispositivos = FragmentoDispositivos.newInstance(true) // solo lectura

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_profesor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profesor"

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_profesor)

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

