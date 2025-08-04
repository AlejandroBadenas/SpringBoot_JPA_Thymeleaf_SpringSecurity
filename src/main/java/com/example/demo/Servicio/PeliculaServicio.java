package com.example.demo.Servicio;

import com.example.demo.Entidad.Pelicula;
import com.example.demo.Entidad.Usuario;
import com.example.demo.Repositorio.PeliculaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServicio {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    public Pelicula agregarPelicula(Pelicula pelicula) {
        // Si necesitas alguna validación antes de agregar la película, puedes hacerla aquí
        return peliculaRepositorio.save(pelicula);
    }

    // Método para ver una película por su ID
    public List<Pelicula> verPelicula() {
        return peliculaRepositorio.findAll(); // Devuelve un Optional con la película o vacío si no la encuentra
    }

    public List<Pelicula> obtenerPeliculasPorUsuario(Usuario usuario) {
        return peliculaRepositorio.findByUsuario(usuario);
    }

    // Obtener película por ID (para editar)
    public Pelicula obtenerPorId(Integer id) {
        Optional<Pelicula> optional = peliculaRepositorio.findById(id);
        return optional.orElse(null);
    }

    // Eliminar película
    public void eliminarPelicula(Integer id) {
        peliculaRepositorio.deleteById(id);
    }

    public List<Pelicula> obtenerPorUsuario(int idUsuario) {
        return peliculaRepositorio.findByUsuarioId(idUsuario);
    }
}
