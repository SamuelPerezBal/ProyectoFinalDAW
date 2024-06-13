package daw.proyecto.hourcontrol.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import daw.proyecto.hourcontrol.domain.Empleado;
import daw.proyecto.hourcontrol.domain.Ficha;
import daw.proyecto.hourcontrol.exceptions.NotFoundException;
import daw.proyecto.hourcontrol.repositories.EmpleadoRepository;
import daw.proyecto.hourcontrol.repositories.FichaRepository;
import daw.proyecto.hourcontrol.services.EmpleadoService;
import daw.proyecto.hourcontrol.services.FichaService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ficha")
public class FichaController {

    @Autowired
    FichaService fichaService;

    @Autowired
    FichaRepository fichaRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping({ "/", "" })
    public String showFicha(Model model) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Empleado empleado = empleadoRepository.findByNombre(authentication.getName());
            model.addAttribute("idEmpleado", empleado.getId());
            if (empleado.getRol().toString() == "ADMIN") {
                model.addAttribute("listaEmpleados", empleadoService.obtenerTodos());
                model.addAttribute("listaFichas", fichaService.obtenerTodos());
            } else {
                model.addAttribute("listaFichas", fichaService.obtenerPorEmpleadoId(empleado.getId()));
            }
        }
        return "ficha/fichaView";

    }

    @GetMapping("/entrada")
    public String ficharEntrada() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Empleado empleado = empleadoRepository.findByNombre(authentication.getName());
        if (empleado != null) {
            LocalDateTime fecha = LocalDateTime.now();

            Ficha nuevaFicha = new Ficha(fecha, empleado);
            empleado.getFichas().add(nuevaFicha);
            empleadoRepository.save(empleado);
        }
        return "redirect:/public";
    }

    @GetMapping("/salida")
    public String ficharSalida() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Empleado empleado = empleadoRepository.findByNombre(authentication.getName());

        if (empleado != null) {
            LocalDateTime fecha = LocalDateTime.now();

            Ficha ultimaFichaSinSalida = null;
            for (Ficha ficha : empleado.getFichas()) {
                if (ficha.getSalida() == null) {
                    ultimaFichaSinSalida = ficha;
                    break;
                }
            }

            if (ultimaFichaSinSalida != null) {
                ultimaFichaSinSalida.setSalida(fecha);
                fichaRepository.save(ultimaFichaSinSalida);
            }
        }

        return "redirect:/public";
    }

    @GetMapping("/export")
    public void exportFichas(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=fichas.csv");
        PrintWriter writer = response.getWriter();
        List<Ficha> fichas = fichaService.obtenerTodos();

        writer.println("Empleado,Fecha,Entrada,Salida,Tiempo Total");
        for (Ficha ficha : fichas) {
            writer.println(String.format("%s,%s,%s,%s,%s",
                    ficha.getEmpleado().getNombre(),
                    ficha.getEntrada().toLocalDate(),
                    ficha.getEntrada().toLocalTime(),
                    ficha.getSalida() != null ? ficha.getSalida().toLocalTime() : "N/A",
                    ficha.getTiempoTotal()));
        }
    }

}
