package com.example.demo.Entidad;

import com.example.demo.Validaciones.Telefono;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column
    @NotBlank(message = "Por favor introduce un nombre")
    private String nombre;

    @Column
    @Email
    @NotBlank(message = "Por favor introduce un correo")
    private String correo;

    @Column
    @NotBlank(message = "Por favor introduce una contraseña")
    private String contraseña;

    @Column
    @Min(value = 18,message = "La edad no puede ser inferior a 18")
    @Max(value = 100,message = "La edad no puede ser superior a 100")
    private int edad;

    @Column
    @NotBlank(message = "Por favor introduce una direccion")
    private String direccion;

    @Column
    @Telefono
    private Integer telefono;

    @Column
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pelicula> peliculas = new ArrayList<>();
}
