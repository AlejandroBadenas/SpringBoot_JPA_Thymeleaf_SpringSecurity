package com.example.demo.Controlador;

import com.example.demo.Entidad.Pelicula;
import com.example.demo.Entidad.Usuario;
import com.example.demo.Servicio.PeliculaServicio;
import com.example.demo.Servicio.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pelicula")
public class PeliculaControlador {

    @Autowired
    private PeliculaServicio peliculaServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/mispeliculas")
    public String mostrarMisPeliculas(Model model, @AuthenticationPrincipal UserDetails userDetails, Authentication authentication) {
        Usuario usuario = usuarioServicio.encontrarPorCorreo(userDetails.getUsername());
        String nombreUsuario = authentication.getName();
        List<Pelicula> listaPeliculas = peliculaServicio.obtenerPeliculasPorUsuario(usuario);
        model.addAttribute("listaPeliculas", listaPeliculas);
        model.addAttribute("nombreUsuario",nombreUsuario);
        return "misPeliculas";
    }

    @GetMapping("/agregarPeliculas")
    public String agregarPeliculas(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        return "agregarPeliculas";
    }

    @PostMapping("/agregarPeliculas")
    public String guardarPelicula(@Valid @ModelAttribute Pelicula pelicula, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if(!bindingResult.hasErrors()){
            Usuario usuario = usuarioServicio.encontrarPorCorreo(userDetails.getUsername());
            pelicula.setUsuario(usuario);
            peliculaServicio.agregarPelicula(pelicula);
            return "redirect:/pelicula/mispeliculas";
        }else {
            return "agregarPeliculas";
        }
    }

    @GetMapping("/editarPeliculas/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Pelicula pelicula = peliculaServicio.obtenerPorId(id);
        model.addAttribute("pelicula", pelicula);
        return "editarPeliculas";
    }

    @PostMapping("/editarPeliculas/{id}")
    public String editarPelicula(@PathVariable Integer id, @Valid @ModelAttribute Pelicula peliculaActualizada, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioServicio.encontrarPorCorreo(userDetails.getUsername());
        Pelicula peliculaExistente = peliculaServicio.obtenerPorId(id);
        if (!bindingResult.hasErrors() && peliculaExistente != null && peliculaExistente.getUsuario().getId_usuario().equals(usuario.getId_usuario())) {

            peliculaActualizada.setId_pelicula(id);
            peliculaActualizada.setUsuario(usuario);
            peliculaServicio.agregarPelicula(peliculaActualizada);
            return "redirect:/pelicula/mispeliculas";
        } else {
            return "editarPeliculas";
        }
    }


    @GetMapping("/eliminarPeliculas/{id}")
    public String eliminarPelicula(@PathVariable Integer id,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioServicio.encontrarPorCorreo(userDetails.getUsername());
        Pelicula pelicula = peliculaServicio.obtenerPorId(id);

        if (pelicula != null && pelicula.getUsuario().getId_usuario().equals(usuario.getId_usuario())) {
            peliculaServicio.eliminarPelicula(id);
        }

        return "redirect:/pelicula/mispeliculas";
    }
}
