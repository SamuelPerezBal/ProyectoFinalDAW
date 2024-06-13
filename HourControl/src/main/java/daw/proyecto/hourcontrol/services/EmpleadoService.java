package daw.proyecto.hourcontrol.services;

import java.util.List;

import org.springframework.stereotype.Service;

import daw.proyecto.hourcontrol.domain.Empleado;
import daw.proyecto.hourcontrol.exceptions.NotFoundException;

@Service
public interface EmpleadoService {

    public Empleado añadir(Empleado usuario);

    public Empleado obtenerPorId(Long id) throws NotFoundException;

    public Empleado editar(Empleado usuario) throws NotFoundException;

    public void borrar(Long id) throws NotFoundException;

    public List<Empleado> obtenerTodos();

}
