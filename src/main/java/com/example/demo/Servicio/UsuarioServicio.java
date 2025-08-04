package com.example.demo.Servicio;

import com.example.demo.Entidad.Pelicula;
import com.example.demo.Entidad.Rol;
import com.example.demo.Entidad.Usuario;
import com.example.demo.Repositorio.PeliculaRepositorio;
import com.example.demo.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    private boolean esPrimerUsuario = true;  // Bandera para identificar si es el primer usuario

    // Registrar un nuevo usuario
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        String contrasenaEncriptada = passwordEncoder.encode(usuario.getContraseña());
        usuario.setContraseña(contrasenaEncriptada);

        // Verificar si es el primer usuario de la base de datos
        boolean primerUsuario = usuarioRepositorio.count() == 0;

        if (primerUsuario) {
            usuario.setRol(Rol.Admin);
        } else {
            usuario.setRol(Rol.Usuario);
        }

        return usuarioRepositorio.save(usuario);
    }


    // Autenticar al usuario (login)
    public Usuario autenticarUsuario(String correo, String contrasena) {
        // Buscar al usuario por correo
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByCorreo(correo);

        // Si el usuario existe y las contraseñas coinciden
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contrasena, usuario.getContraseña())) {
                return usuario;  // Autenticación exitosa
            }
        }
        return null; // Si no se encuentra el usuario o las contraseñas no coinciden
    }

    public List<Usuario> listarTodos() {
        return usuarioRepositorio.findAll();
    }

    public Usuario encontrarPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }

    // Obtener un usuario por su ID
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // Obtener todas las películas asociadas a un usuario
    public List<Pelicula> obtenerPeliculasPorUsuario(Integer idUsuario) {
        return peliculaRepositorio.findByUsuarioId(idUsuario);
    }

    public Usuario obtenerPorUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con nombre de usuario: " + nombreUsuario));
    }

}
