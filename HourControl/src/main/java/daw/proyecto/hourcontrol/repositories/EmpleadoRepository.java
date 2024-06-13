package daw.proyecto.hourcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.proyecto.hourcontrol.domain.Empleado;



public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Empleado findByNombre(String nombre);

}
