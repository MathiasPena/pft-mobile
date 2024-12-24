package com.utec.pft

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.utec.pft.databinding.ActivityVistaPrincipalBinding
import com.utec.pft.objetos.Reclamo
import com.utec.pft.ui.agregarReclamo.AgregarReclamoFragment

class VistaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityVistaPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad y configurar la vista
        binding = ActivityVistaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la barra de herramientas y desactivar el título automático
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)



        // Configurar la navegación con BottomNavigationView
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_vista_principal)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_perfil, R.id.navigation_agregar, R.id.navigation_listar
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Actualizar el título en el segundo ConstraintLayout según el fragmento actual
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitle.text = destination.label.toString()
        }

        // Manejar el botón de cierre de sesión
        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    // Función para cerrar la sesión
    private fun logout() {
        startActivity(Intent(this, Login::class.java))
    }

    // Mostrar un cuadro de diálogo de confirmación al cerrar la sesión
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("¿Seguro de que deseas cerrar la sesión?")
            .setPositiveButton("Estoy seguro") { _, _ -> logout() }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}