package com.utec.pft.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utec.pft.complementos.dataTransfer.DataTransfer
import com.utec.pft.databinding.FragmentPerfilBinding
import com.utec.pft.objetos.Area
import com.utec.pft.objetos.Departamento
import com.utec.pft.objetos.Itr
import com.utec.pft.objetos.Rol
import com.utec.pft.objetos.Usuario

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //Obtengo el Usuario desde DataTransfer que utilizamos en el Login
        val usuarioApi = DataTransfer.getInstance().obtenerUsuario()

        //Usuario harcodeado para prueba y de backup
        val rol = Rol(idRol = 1, nombre = "Rol de Prueba")
        val area = Area(idArea = 1, nombre = "Área de Prueba")
        val dep = Departamento(idDep = 1, nombre = "Departamento de Prueba")
        val itr = Itr(descripcion = "Itr de Prueba", idItr = 1, nombre = "Nombre de Itr")

        val usuario = Usuario(
            anio = 1990,
            apellido1 = "Apellido",
            area = area,
            contrasenia = "contraseña123",
            dep = dep,
            documento = "123456789",
            eliminado = false,
            email = "usuario@example.com",
            emailp = "email_personal@example.com",
            fechaNacimiento = "1990-01-01",
            fechaRegistro = "2023-11-02",
            idUsuario = 1,
            itr = itr,
            nombre1 = "Nombre",
            nombreUsuario = "nombre_usuario",
            rol = rol,
            telefono = "123-456-7890",
            validado = true
        )


        //Seteo los campos en los TextView

        if (usuarioApi!=null) {
            binding.campoNombre.text = usuarioApi.nombre1
            binding.campoApellido.text = usuarioApi.apellido1
            binding.campoUsuario.text = usuarioApi.nombreUsuario
            binding.campoRol.text = usuarioApi.rol.nombre
            binding.campoDoc.text = usuarioApi.documento
            binding.campoTelefono.text = usuarioApi.telefono
            binding.campoEmail.text = usuarioApi.email
            binding.campoFechaNac.text = usuarioApi.fechaNacimiento
        } else {
            // Si algo falla, dejo datos de prueba para poner algo
            binding.campoNombre.text = usuario.nombre1
            binding.campoApellido.text = usuario.apellido1
            binding.campoUsuario.text = usuario.nombreUsuario
            binding.campoRol.text = usuario.rol.nombre
            binding.campoDoc.text = usuario.documento
            binding.campoTelefono.text = usuario.telefono
            binding.campoEmail.text = usuario.email
            binding.campoFechaNac.text = usuario.fechaNacimiento
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}