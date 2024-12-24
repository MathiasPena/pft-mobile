package com.utec.pft.ui.listarReclamosAnalista

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utec.pft.Login
import com.utec.pft.R
import com.utec.pft.api.RetrofitClient
import com.utec.pft.api.response.ReclamoResponse
import com.utec.pft.api.response.convertirReclamoResponseAReclamo
import com.utec.pft.api.response.convertirReclamoResponseAReclamoAnalista
import com.utec.pft.complementos.adaptadores.ReclamoAdapter
import com.utec.pft.complementos.adaptadores.ReclamoAnalistaAdapter
import com.utec.pft.databinding.ActivityListarReclamosAnalistaBinding
import com.utec.pft.objetos.Reclamo
import com.utec.pft.objetos.ReclamoAnalista
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListarReclamosAnalista : AppCompatActivity() {

    private lateinit var binding: ActivityListarReclamosAnalistaBinding
    private lateinit var adapter: ReclamoAnalistaAdapter
    private lateinit var listaOriginal: List<ReclamoAnalista>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarReclamosAnalistaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Seteo el titulo
        val title = "Listar Reclamos"
        binding.toolbarTitle3.text = title

        // Inicializar el adaptador con una lista vacía
        val itemSpacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing_between_items)
        adapter = ReclamoAnalistaAdapter(emptyList(), itemSpacingInPixels)

        // Configurar el RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView2
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Configurar el filtro
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // No es necesario implementar este método
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Filtrar la lista de reclamos y actualizar el RecyclerView
                val filterText = charSequence.toString().trim()
                val filteredList = if (filterText.isNotEmpty()) {
                    listaOriginal.filter { reclamo ->
                        reclamo.titulo.contains(filterText, ignoreCase = true)
                    }
                } else {
                    // Si el texto del filtro está vacío, mostrar la lista completa
                    listaOriginal
                }
                adapter.actualizar(filteredList)
            }

            override fun afterTextChanged(editable: Editable) {
                // No es necesario implementar este método
            }
        })

        // Llamada a la API para obtener la lista de reclamos
        obtenerListaReclamosDesdeAPI()

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

    // Función para obtener la lista de reclamos desde la API
    private fun obtenerListaReclamosDesdeAPI() {

        // Llamada a la API
        val call = RetrofitClient.apiService.obtenerReclamosAnalista()

        call.enqueue(object : Callback<List<ReclamoResponse>> {
            override fun onResponse(
                call: Call<List<ReclamoResponse>>,
                response: Response<List<ReclamoResponse>>
            ) {
                if (response.isSuccessful) {
                    val reclamos =
                        response.body()?.map { convertirReclamoResponseAReclamoAnalista(it) }
                            ?: emptyList()
                    // Guardar la lista original y actualizar el adaptador
                    listaOriginal = reclamos
                    adapter.actualizar(reclamos)
                } else {
                    // Manejar el error de la respuesta de la API
                }
            }

            override fun onFailure(call: Call<List<ReclamoResponse>>, t: Throwable) {
                // Manejar el fallo de la llamada a la API
            }
        })
    }
}