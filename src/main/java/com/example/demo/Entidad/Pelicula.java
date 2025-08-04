package com.example.demo.Entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Peliculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pelicula;

    @Column
    @NotBlank(message = "Introduce un titulo")
    private String titulo;

    @Column
    @NotBlank(message = "Introduce un director")
    private String director;

    @Column
    @Min(value = 1900,message = "No se puede introducir peliculas de antes del 1900")
    @Max(value = 2025, message = "Estamos a 2025")
    private Integer año;

    @Column
    @NotBlank(message = "Introduce un genero")
    private String genero;

    @Column
    @Min(value = 0, message = "No se puede introducir valoracion negativa")
    @Max(value = 10, message = "No se puede introducir una valoracion superior a 10")
    private Integer valoracion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
