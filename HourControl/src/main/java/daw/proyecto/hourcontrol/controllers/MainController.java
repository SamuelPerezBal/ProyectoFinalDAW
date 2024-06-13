package daw.proyecto.hourcontrol.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import daw.proyecto.hourcontrol.domain.Empleado;
import daw.proyecto.hourcontrol.repositories.EmpleadoRepository;

@Controller
@RequestMapping("/public")

public class MainController {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @GetMapping({ "/index", "/home", "/", "" })
    public String showHome(@RequestParam Optional<String> nombre, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Empleado Empleado = empleadoRepository.findByNombre(authentication.getName());
            model.addAttribute("idEmpleado", Empleado.getId());
        }
        return "indexView";
    }

    @GetMapping("/accessError")
    public String showAccessError() {
        return "error/403";
    }

    @GetMapping("/aviso-legal")
    public String showAvisoLegal() {
        return "/footer/avisoLegalView";
    }

    @GetMapping("/politica-de-privacidad")
    public String showPoliticaPrivacidad() {
        return "/footer/politicaView";
    }

    @GetMapping("/contacto")
    public String showContacto() {
        return "/footer/contactoView";
    }
}


