package daw.proyecto.hourcontrol.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")

@Entity
public class Empleado {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String nombre;

    @Size(min = 4)
    private String clave;

    private Rol rol;

    @NotNull
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Ficha> fichas;

    public Empleado(@NotEmpty String nombre, String clave, Rol rol) {
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
        this.fechaRegistro = LocalDate.now();
    }

    public Empleado() {
        this.fechaRegistro = LocalDate.now();
    }
    
}
