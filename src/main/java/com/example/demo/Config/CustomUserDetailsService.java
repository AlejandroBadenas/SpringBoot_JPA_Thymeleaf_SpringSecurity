package com.example.demo.Config;

import com.example.demo.Entidad.Usuario;
import com.example.demo.Repositorio.UsuarioRepositorio;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public CustomUserDetailsService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder() //Cargamos el user que hace login y obtenemos su correo y contraseña asi como el rol que tiene
                .username(usuario.getCorreo())  // Usamos el correo como nombre de usuario
                .password(usuario.getContraseña())  // La contraseña está encriptada en la base de datos
                .roles(usuario.getRol() != null ? new String[]{usuario.getRol().name()} : new String[]{})  // Solo usamos el nombre del rol (sin "ROLE_")
                .build();
    }


}
