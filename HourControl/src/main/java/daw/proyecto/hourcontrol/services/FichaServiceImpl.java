package daw.proyecto.hourcontrol.services;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.proyecto.hourcontrol.domain.Ficha;
import daw.proyecto.hourcontrol.exceptions.NotFoundException;
import daw.proyecto.hourcontrol.repositories.FichaRepository;

@Service
public class FichaServiceImpl implements FichaService {

    @Autowired
    private FichaRepository fichaRepository;

    @Override
    public List<Ficha> obtenerTodos() {
        return fichaRepository.findAll().stream().map(this::calcularTiempoTotal).collect(Collectors.toList());
    }

    @Override
    public Ficha añadir(Ficha ficha) {
        return fichaRepository.save(ficha);
    }

    @Override
    public Ficha obtenerPorId(Long id) throws NotFoundException {
        return fichaRepository.findById(id).map(this::calcularTiempoTotal).orElseThrow(() -> new NotFoundException("Ficha no encontrada"));
    }

    @Override
    public Ficha editar(Ficha ficha) throws NotFoundException {
        if (!fichaRepository.existsById(ficha.getId())) {
            throw new NotFoundException("Ficha no encontrada");
        }
        return fichaRepository.save(ficha);
    }

    @Override
    public void borrar(Long id) throws NotFoundException {
        if (!fichaRepository.existsById(id)) {
            throw new NotFoundException("Ficha no encontrada");
        }
        fichaRepository.deleteById(id);
    }

    @Override
    public List<Ficha> obtenerPorEmpleadoId(Long empleadoId) throws NotFoundException {
        List<Ficha> fichas = fichaRepository.findByEmpleadoId(empleadoId).orElseThrow(() -> new NotFoundException("No se encontraron fichas para el empleado con id: " + empleadoId));
        return fichas.stream().map(this::calcularTiempoTotal).collect(Collectors.toList());
    }

    private Ficha calcularTiempoTotal(Ficha ficha) {
        if (ficha.getEntrada() != null && ficha.getSalida() != null) {
            Duration duration = Duration.between(ficha.getEntrada(), ficha.getSalida());
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;
            String tiempoTotal = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            ficha.setTiempoTotal(tiempoTotal);
        } else {
            ficha.setTiempoTotal("N/A");
        }
        return ficha;
    }
}
