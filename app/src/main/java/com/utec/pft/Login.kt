package com.utec.pft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.utec.pft.api.RetrofitClient
import com.utec.pft.api.response.LoginRequest
import com.utec.pft.api.response.LoginResponse
import com.utec.pft.complementos.dataTransfer.DataTransfer
import com.utec.pft.databinding.ActivityLoginBinding
import com.utec.pft.ui.listarReclamosAnalista.ListarReclamosAnalista
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    // Declaración de la propiedad de binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad y configurar la vista con el binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el botón de inicio de sesión
        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        // Obtener los valores de los campos de correo electrónico y contraseña
        val email = binding.editTextEmail.text.toString()
        val pass = binding.editTextPassword.text.toString()

        // Crear un objeto de solicitud de inicio de sesión con los datos ingresados
        val loginRequest = LoginRequest(email, pass)

        // Realizar una llamada POST al servidor con los datos de inicio de sesión
        val call = RetrofitClient.apiService.login(loginRequest)

        // Manejar la respuesta de la llamada
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {

                        //Guardo el rol para verificarlo
                        val rol = loginResponse.rol.nombre
                        //Verifico el rol para seleccionar que vista iniciar, este caso es Estudiante
                        if (rol == "Estudiante") {


                            //Guardo el usuario para el fragmento de Perfil
                            DataTransfer.getInstance().guardarUsuario(loginResponse)
                            //Guardo el id para manejarlo en otras partes del programa de manera individual
                            DataTransfer.getInstance().guardarIdusuario(loginResponse.idUsuario)


                            //Mensaje de login en un toast corto
                            showShortToast(
                                mensajeLogin(
                                    loginResponse.nombre1
                                )
                            )

                            //Abro vista principal
                            redirectToVistaPrincipal()

                        } else if (rol == "Analista"){

                            //Guardo el id para manejarlo en otras partes del programa de manera individual
                            DataTransfer.getInstance().guardarIdusuario(loginResponse.idUsuario)

                            //Mensaje de login en un toast corto
                            showShortToast(
                                mensajeLogin(
                                    loginResponse.nombre1
                                )
                            )

                            //Abro vista principal
                            redirectToListarAnalista()

                        } else {
                            //Usuario que no es Estudiante o Analista
                            showLongToast("Su tipo de usuario no corresponde a la aplicación")
                        }

                    } else {
                        //Error con la comunicación de la API
                        showLongToast("Error, objeto de respuesta nulo, comuniquese con soporte")
                    }

                } else {
                    // Mensaje de error para casos de inicio de sesión fallidos
                    val errorMessage =
                        "Error, verifique los datos ingresados. Quizás su usuario aún no esté activado"
                    showLongToast(errorMessage)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Mensaje de error en caso de problemas de conexión
                showShortToast("Error, problemas de conexión, comuniquese con soporte")
            }
        })
    }

    // Función para mostrar un Toast con duración corta
    private fun showShortToast(message: String) {
        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
    }

    // Función para mostrar un Toast con duración larga
    private fun showLongToast(message: String) {
        Toast.makeText(this@Login, message, Toast.LENGTH_LONG).show()
    }

    // Función para redirigir a la actividad VistaPrincipal
    private fun redirectToVistaPrincipal() {
        val intent = Intent(this@Login, VistaPrincipal::class.java)

        startActivity(intent)
    }
    // Función para redirigir a la actividad Listar Analista
    private fun redirectToListarAnalista() {
        val intent = Intent(this@Login, ListarReclamosAnalista::class.java)
        startActivity(intent)
    }
    // Función para el mensaje del Login
    private fun mensajeLogin(nombre: String): String {
        return "Login exitoso, Bienvenido, $nombre"
    }


}

