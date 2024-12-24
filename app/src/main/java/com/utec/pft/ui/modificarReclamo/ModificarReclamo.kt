package com.utec.pft.ui.modificarReclamo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.utec.pft.Login
import com.utec.pft.R
import com.utec.pft.api.RetrofitClient
import com.utec.pft.api.response.ModificarReclamoRequest
import com.utec.pft.api.response.ModificarReclamoResponse
import com.utec.pft.complementos.DatePickerHelper
import com.utec.pft.complementos.dataTransfer.DataTransfer
import com.utec.pft.databinding.ActivityModificarReclamoBinding
import com.utec.pft.objetos.Reclamo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModificarReclamo : AppCompatActivity() {

    private lateinit var binding: ActivityModificarReclamoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarReclamoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title = "Modificar Reclamo"
        binding.toolbarTitle2.text = title

        val datePickerHelper = DatePickerHelper(this)

        val fecha2 = binding.editFechaRegistro

        fecha2.setOnClickListener {
            datePickerHelper.showDatePickerDialog(fecha2)
        }

        // Recibe el reclamo seleccionado
        val reclamo = intent.getParcelableExtra("reclamoSeleccionado", Reclamo::class.java)

        // Boton hacia atras
        val backArrowIcon = binding.backArrowIcon
        backArrowIcon.setOnClickListener {
            finish()
        }

        if (reclamo != null) {
            val semestre = reclamo.semestre
            val semestresArray = resources.getStringArray(R.array.Semestre)
            val position = semestresArray.indexOf(semestre)

            binding.editTitulo.setText(reclamo.titulo)
            binding.editDescripcion.setText(reclamo.desc)
            binding.editFecha.setText(reclamo.fechaYHora)

            if (position != -1) {
                binding.spinnerSemestre.setSelection(position)
            } else {
                // Manejar el caso en que el semestre no se encuentra en la lista del Spinner
            }

            binding.editFechaRegistro.setText(reclamo.fechaRegistro)
            binding.editDocente.setText(reclamo.docente)
            binding.editCreditos.setText(reclamo.creditos)
        }

        val botonModificar = binding.btnAceptarModificar
        botonModificar.setOnClickListener {
            modificarReclamo(reclamo)
        }

        binding.logoutButton1.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun modificarReclamo(reclamo: Reclamo?) {
        if (reclamo != null) {
            val tipoReclamo = binding.spinnerTipoReclamo.selectedItem.toString()
            val titulo = binding.editTitulo.text.toString()
            val desc = binding.editDescripcion.text.toString()
            val fechaYHoraReclamo = binding.editFecha.text.toString()
            val semestre = binding.spinnerSemestre.selectedItem.toString()
            val fechaRegistro = binding.editFechaRegistro.text.toString()
            val docente = binding.editDocente.text.toString()
            val creditos = binding.editCreditos.text.toString()

            // Verificar que todos los campos estén completos
            if (tipoReclamo.isNotEmpty() && titulo.isNotEmpty() && desc.isNotEmpty()
                && fechaYHoraReclamo.isNotEmpty() && semestre.isNotEmpty()
                && fechaRegistro.isNotEmpty() && docente.isNotEmpty() && creditos.isNotEmpty()
            ) {
                val idReclamo = reclamo.id.toInt()
                val idUsuario = DataTransfer.getInstance().obtenerIdusuario()

                val modificarReclamoRequest = ModificarReclamoRequest(
                    tipoReclamo,
                    titulo,
                    desc,
                    fechaRegistro,
                    docente,
                    semestre,
                    fechaYHoraReclamo,
                    creditos,
                    idUsuario
                )

                // Realizar una llamada POST al servidor con los datos del reclamo
                val call = RetrofitClient.apiService.modificarReclamo(idReclamo, modificarReclamoRequest)

                // Manejar la respuesta de la llamada
                call.enqueue(object : Callback<ModificarReclamoResponse> {
                    override fun onResponse(
                        call: Call<ModificarReclamoResponse>,
                        response: Response<ModificarReclamoResponse>
                    ) {
                        if (response.isSuccessful) {
                            val modificarReclamoResponse = response.body()
                            if (modificarReclamoResponse != null) {
                                showShortToast("Reclamo modificado satisfactoriamente")
                                DataTransfer.getInstance().guardarEstado(1)
                                finish()
                            } else {
                                // Error con la comunicación de la API
                                showShortToast("Error, objeto de respuesta nulo, comuníquese con soporte")
                                Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                            }
                        } else {
                            // Mensaje de error
                            val errorMessage = "Error, conexión no válida, comuníquese con soporte"
                            showShortToast(errorMessage)
                            Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<ModificarReclamoResponse>, t: Throwable) {
                        showShortToast("Error, verifique su conexión o comuníquese con soporte")
                        Log.e("API_ERROR", "Error en la llamada: ${t.message}", t)
                    }
                })
            } else {
                // Mostrar un mensaje indicando que algunos campos están vacíos
                showShortToast("Todos los campos son obligatorios")
            }
        }
    }

    private fun logout() {
        startActivity(Intent(this, Login::class.java))
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("¿Seguro de que deseas cerrar la sesión?")
            .setPositiveButton("Estoy seguro") { _, _ -> logout() }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun showShortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



