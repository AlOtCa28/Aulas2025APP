package com.example.aulas2025app.JEFE

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
import com.example.aulas2025app.LOGIN.LoginActivity
import com.example.aulas2025app.R
import com.example.aulas2025app.databinding.ActivityJefeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class JefeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJefeBinding
    private lateinit var jefeViewModel: JefeViewModel
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJefeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mtbMenu)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.miFragContainer) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.btvMenu
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profesores, R.id.navigation_Aulas, R.id.navigation_dispositivos
            )
        )
        navView.setupWithNavController(navController)

        supportActionBar?.title = "JEFES"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }
}
