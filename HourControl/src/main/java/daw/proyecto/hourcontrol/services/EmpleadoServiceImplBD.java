package daw.proyecto.hourcontrol.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import daw.proyecto.hourcontrol.domain.Empleado;
import daw.proyecto.hourcontrol.exceptions.NotFoundException;
import daw.proyecto.hourcontrol.repositories.EmpleadoRepository;

@Service
public class EmpleadoServiceImplBD implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Empleado añadir(Empleado empleado) {
        String passCrypted = passwordEncoder.encode(empleado.getClave());
        empleado.setClave(passCrypted);
        try {
            return empleadoRepository.save(empleado);
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    public Empleado obtenerPorId(Long id) throws NotFoundException {
        return empleadoRepository.findById(id).orElseThrow();
    }

    public Empleado editar(Empleado empleado) throws NotFoundException {
        String passCrypted = passwordEncoder.encode(empleado.getClave());
        empleado.setClave(passCrypted);
        try {
            return empleadoRepository.save(empleado);
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void borrar(Long id) throws NotFoundException {
        empleadoRepository.deleteById(id);
    }

}
