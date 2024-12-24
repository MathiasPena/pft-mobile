package com.utec.pft.ui.listarReclamos

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utec.pft.R
import com.utec.pft.api.RetrofitClient
import com.utec.pft.api.response.BorrarReclamoResponse
import com.utec.pft.api.response.ReclamoResponse
import com.utec.pft.api.response.convertirReclamoResponseAReclamo
import com.utec.pft.complementos.adaptadores.ReclamoAdapter
import com.utec.pft.complementos.dataTransfer.DataTransfer
import com.utec.pft.databinding.FragmentListarReclamosBinding
import com.utec.pft.objetos.Reclamo
import com.utec.pft.ui.modificarReclamo.ModificarReclamo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListarReclamosFragment : Fragment() {

    private var _binding: FragmentListarReclamosBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ReclamoAdapter
    private var selectedPosition = RecyclerView.NO_POSITION

    private lateinit var reclamos: List<Reclamo>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListarReclamosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reclamos = emptyList()
        val itemSpacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing_between_items)
        val btnModificar = binding.btnModificar
        val btnEliminar = binding.btnEliminar
        val recyclerView: RecyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = ReclamoAdapter(emptyList(), itemSpacingInPixels)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val idUsuario = DataTransfer.getInstance().obtenerIdusuario()
        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val selectButton = AnimationUtils.loadAnimation(requireContext(), R.anim.select_button)

        // Configurar un listener para el adaptador cuando se selecciona un elemento
        adapter.setOnItemClickListener { position ->
            if (position != RecyclerView.NO_POSITION) {

                selectedPosition = position

                val selectedReclamo = adapter.getItemAtPosition(selectedPosition)
                if (selectedReclamo.estado == "Cerrado") {
                    btnModificar.visibility = View.INVISIBLE
                    btnEliminar.visibility = View.INVISIBLE
                    showShortToast("Reclamo cerrado, no se puede modificar ni eliminar")
                } else {
                    // Ejecutar la animación y mostrar el botón
                    btnModificar.startAnimation(fadeInAnimation)
                    btnEliminar.startAnimation(fadeInAnimation)
                    btnModificar.visibility = View.VISIBLE
                    btnEliminar.visibility = View.VISIBLE
                }
            }
        }

        btnModificar.setOnClickListener {
            if (selectedPosition != RecyclerView.NO_POSITION) {
                val selectedReclamo = adapter.getItemAtPosition(selectedPosition)
                btnModificar.startAnimation(selectButton)
                val intent = Intent(requireContext(), ModificarReclamo::class.java)
                intent.putExtra("reclamoSeleccionado", selectedReclamo)
                startActivity(intent)
            }
        }

        btnEliminar.setOnClickListener {
            if (selectedPosition != RecyclerView.NO_POSITION) {
                val selectedReclamo = adapter.getItemAtPosition(selectedPosition)
                btnEliminar.startAnimation(selectButton)
                confirmarEliminacion(selectedReclamo)
            }
        }

        val editTextFilter = binding.editTextSearch
        editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filterText = s.toString().trim()
                val filteredList = if (filterText.isNotEmpty()) {
                    reclamos.filter { reclamo -> reclamo.titulo.contains(filterText, true) }
                } else {
                    reclamos
                }
                adapter.actualizar(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        actualizarLista(idUsuario)

        return root
    }

    private fun confirmarEliminacion(selectedReclamo: Reclamo) {
        AlertDialog.Builder(requireContext())
            .setTitle("¿Seguro de que deseas eliminar el reclamo?")
            .setPositiveButton("Estoy seguro") { _, _ ->
                borrarReclamo(selectedReclamo)
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun actualizarLista(idUsuario: Int) {
        val call = RetrofitClient.apiService.obtenerReclamos(idUsuario)
        call.enqueue(object : Callback<List<ReclamoResponse>> {
            override fun onResponse(
                call: Call<List<ReclamoResponse>>,
                response: Response<List<ReclamoResponse>>
            ) {
                if (response.isSuccessful) {
                    reclamos =
                        response.body()?.map { convertirReclamoResponseAReclamo(it) } ?: emptyList()
                    adapter.actualizar(reclamos)
                } else {
                    Log.e(
                        "API_ERROR",
                        "Error en la respuesta: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<ReclamoResponse>>, t: Throwable) {
                Log.e("API_ERROR", "Error en la llamada: ${t.message}", t)
            }
        })
    }

    private fun borrarReclamo(selectedReclamo: Reclamo) {
        val call = RetrofitClient.apiService.eliminarReclamo(selectedReclamo.id)
        call.enqueue(object : Callback<BorrarReclamoResponse> {
            override fun onResponse(
                call: Call<BorrarReclamoResponse>,
                response: Response<BorrarReclamoResponse>
            ) {
                if (response.isSuccessful) {
                    showShortToast("Reclamo eliminado correctamente")
                    actualizarLista(DataTransfer.getInstance().obtenerIdusuario())
                    binding.btnModificar.visibility = View.INVISIBLE
                    binding.btnEliminar.visibility = View.INVISIBLE
                } else {
                    showShortToast("Error al borrar, comuníquese con soporte")
                    Log.e(
                        "API_ERROR",
                        "Error en la respuesta: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<BorrarReclamoResponse>, t: Throwable) {
                showShortToast("Error al borrar, comuníquese con soporte")
                Log.e("API_ERROR", "Error en la llamada: ${t.message}", t)
            }
        })
    }

    private fun showShortToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        // Obtén el estado actualizado
        val estadoModificado = DataTransfer.getInstance().obtenerEstado()

        if (estadoModificado == 1) {
            // Realiza la actualización solo si el estado indica que se hizo una modificación
            actualizarLista(DataTransfer.getInstance().obtenerIdusuario())
        }

        // Restablece el estado a 0 después de verificarlo
        DataTransfer.getInstance().guardarEstado(0)

        // Vacía el EditText de filtrar
        binding.editTextSearch.text.clear()
        binding.btnModificar.visibility = View.INVISIBLE
        binding.btnEliminar.visibility = View.INVISIBLE
    }
}