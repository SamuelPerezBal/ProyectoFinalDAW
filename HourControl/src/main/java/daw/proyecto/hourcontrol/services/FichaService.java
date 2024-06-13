package daw.proyecto.hourcontrol.services;

import java.util.List;

import daw.proyecto.hourcontrol.domain.Ficha;
import daw.proyecto.hourcontrol.exceptions.NotFoundException;

public interface FichaService {
    List<Ficha> obtenerTodos();
    Ficha añadir(Ficha ficha);
    Ficha obtenerPorId(Long id) throws NotFoundException;
    Ficha editar(Ficha ficha) throws NotFoundException;
    void borrar(Long id) throws NotFoundException;
    List<Ficha> obtenerPorEmpleadoId(Long empleadoId) throws NotFoundException;
}
