package com.utec.pft.ui.agregarReclamo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.utec.pft.api.RetrofitClient
import com.utec.pft.api.response.AgregarReclamoRequest
import com.utec.pft.api.response.AgregarReclamoResponse
import com.utec.pft.complementos.DatePickerHelper
import com.utec.pft.complementos.DateTime
import com.utec.pft.complementos.convertirFormatoFecha
import com.utec.pft.complementos.dataTransfer.DataTransfer
import com.utec.pft.databinding.FragmentAgregarReclamoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarReclamoFragment : Fragment() {

    private var _binding: FragmentAgregarReclamoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarReclamoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Helper para el selector de fechas
        val datePickerHelper = DatePickerHelper(this.requireContext())
        // Helper para la fecha y hora actual
        val dateTimeHelper = DateTime(this.requireContext())

        // Referencias a los elementos de la vista
        val fecha1 = binding.editFecha
        val fecha2 = binding.editFechaRegistro

        // Obtener la fecha y hora actual y mostrarla en el campo "fecha1"
        val formattedDateTime = dateTimeHelper.getCurrentDateTimeFormatted("dd/MM/yyyy")
        fecha1.setText(formattedDateTime)

        // Configurar un listener para el campo "fecha2" que muestra un selector de fechas
        fecha2.setOnClickListener {
            datePickerHelper.showDatePickerDialog(fecha2)
        }

        // Configurar un listener para el botón "Agregar" que captura los valores y crea un objeto Reclamo
        val btnAgregar = binding.btnAgregar
        btnAgregar.setOnClickListener {
            agregarReclamo()
        }
    }

    private fun agregarReclamo() {
        // Obtener el texto de los campos
        val tipoReclamo = binding.spinnerTipoReclamo.selectedItem.toString()
        val titulo = binding.editTitulo.text.toString()
        val desc = binding.editDescripcion.text.toString()
        val fechaYHoraReclamoOriginal = binding.editFecha.text.toString()
        val semestre = binding.spinnerSemestre.selectedItem.toString()
        val fechaRegistroOriginal = binding.editFechaRegistro.text.toString()
        val docente = binding.editDocente.text.toString()
        val creditos = binding.editCreditos.text.toString()

        // Verificar que todos los campos estén completos
        if (tipoReclamo.isNotEmpty() && titulo.isNotEmpty() && desc.isNotEmpty()
            && fechaYHoraReclamoOriginal.isNotEmpty() && semestre.isNotEmpty()
            && fechaRegistroOriginal.isNotEmpty() && docente.isNotEmpty()
            && creditos.isNotEmpty()
        ) {
            // Obtengo el idusuario desde DataTransfer que utilizamos en el Login
            val idUsuario = DataTransfer.getInstance().obtenerIdusuario()

            val fechaYHoraReclamo = convertirFormatoFecha(fechaYHoraReclamoOriginal)
            val fechaRegistro = convertirFormatoFecha(fechaRegistroOriginal)

            // Crear un objeto reclamo para mandarlo al servidor
            val agregarReclamoRequest = AgregarReclamoRequest(
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
            val call = RetrofitClient.apiService.agregarReclamo(agregarReclamoRequest)

            // Manejar la respuesta de la llamada
            call.enqueue(object : Callback<AgregarReclamoResponse> {
                override fun onResponse(
                    call: Call<AgregarReclamoResponse>,
                    response: Response<AgregarReclamoResponse>
                ) {
                    if (response.isSuccessful) {
                        val agregarReclamoResponse = response.body()
                        if (agregarReclamoResponse != null) {
                            showShortToast("Reclamo agregado satisfactoriamente")
                            limpiarCampos()
                        } else {
                            // Error con la comunicación de la API
                            showShortToast("Error, objeto de respuesta nulo, comuníquese con soporte")
                        }
                    } else {
                        // Mensaje de error
                        val errorMessage =
                            "Error, conexión no válida, comuníquese con soporte"
                        showLongToast(errorMessage)
                    }
                }

                override fun onFailure(call: Call<AgregarReclamoResponse>, t: Throwable) {
                    showLongToast("Error, verifique su conexión o comuníquese con soporte")
                    Log.e("API_ERROR", "Error en la llamada: ${t.message}", t)
                }
            })
        } else {
            // Mostrar un mensaje indicando que algunos campos están vacíos
            showShortToast("Todos los campos son obligatorios")
        }
    }

    // Función para mostrar un Toast con duración corta
    private fun showShortToast(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // Función para mostrar un Toast con duración larga
    private fun showLongToast(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        binding.editTitulo.text.clear()
        binding.editDescripcion.text.clear()
        binding.spinnerTipoReclamo.setSelection(0)
        binding.spinnerSemestre.setSelection(0)
        binding.editFechaRegistro.text.clear()
        binding.editDocente.text.clear()
        binding.editCreditos.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}