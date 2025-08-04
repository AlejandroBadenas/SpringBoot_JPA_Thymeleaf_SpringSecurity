package com.example.demo.Repositorio;

import com.example.demo.Entidad.Pelicula;
import com.example.demo.Entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeliculaRepositorio extends JpaRepository<Pelicula,Integer> {
    List<Pelicula> findByUsuario(Usuario usuario);
}
