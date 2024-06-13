package daw.proyecto.hourcontrol.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Ficha {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDateTime entrada;

    private LocalDateTime salida;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Transient
    private String tiempoTotal;

    public Ficha(@NotNull LocalDateTime entrada, Empleado empleado) {
        this.entrada = entrada;
        this.empleado = empleado;
    }

    public Ficha() {
        this.entrada = LocalDateTime.now();
    }
}
