package com.example.demo.Controlador;

import com.example.demo.Entidad.Pelicula;
import com.example.demo.Entidad.Usuario;
import com.example.demo.Servicio.PeliculaServicio;
import com.example.demo.Servicio.UsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {

    @Autowired
    private final UsuarioServicio usuarioServicio;
    @Autowired
    private final PeliculaServicio peliculaServicio;

    // Página de login (Spring Security se encarga del proceso de login)
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        return "login"; // Retorna la vista de login (login.html)
    }

    // Metodo para mostrar la página de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Metodo para manejar el proceso de registro de un nuevo usuario
    @PostMapping("/registro")
    public String registrarUsuario(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "registro";
        }
        usuarioServicio.registrarUsuario(usuario);
        return "redirect:/usuario/login";
    }

    // Metodo para mostrar el panel de control para usuarios Admin
    @GetMapping("/panelcontrol")
    public String mostrarPanelControlAdmin(Model model) {
        return "panelcontrol"; // Retorna la vista del panel de control (panelcontrol.html)
    }

    // Metodo para mostrar la página principal para usuarios
    @GetMapping("/principal")
    public String mostrarPaginaPrincipal(Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        List<Pelicula> listaPelicula = peliculaServicio.verPelicula();
        model.addAttribute("nombreUsuario",nombreUsuario);
        model.addAttribute("listaPelicula",listaPelicula);
        return "principal";
    }

    // Metodo para manejar la acción de logout
    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext(); // Limpia el contexto de seguridad
        return "redirect:/usuario/login?logout"; // Redirige a la página de login después de logout
    }

    // Metodo que puedes agregar para manejar el login, si es necesario
    // Pero Spring Security se encarga de este paso por defecto
    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        // Puedes hacer algún procesamiento adicional aquí si lo necesitas.
        // Pero por lo general, Spring Security ya manejará la autenticación automáticamente.
        return "redirect:/"; // Redirigir a la página de inicio después de un login exitoso
    }

    @GetMapping("/panelcontrol/usuarios")
    public String mostrarUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuarioServicio.listarTodos();

        // Depuración: Verifica si la lista de usuarios está vacía
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            System.out.println("No hay usuarios disponibles.");
        }

        model.addAttribute("listaUsuarios", listaUsuarios);
        return "usuarios";
    }

    @GetMapping("/panelcontrol/peliculas")
    public String mostrarPeliculas(Model model){
        List<Pelicula> listaPeliculas = peliculaServicio.verPelicula();

        if(listaPeliculas == null || listaPeliculas.isEmpty()){
            System.out.println("No hay peliculas disponibles");
        }
        model.addAttribute("listaPeliculas",listaPeliculas);
        return "peliculas";
    }



}
