package daw.proyecto.hourcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import daw.proyecto.hourcontrol.domain.Ficha;

public interface FichaRepository extends JpaRepository<Ficha, Long>{
    Optional<List<Ficha>> findByEmpleadoId(Long empleadoId);
}
