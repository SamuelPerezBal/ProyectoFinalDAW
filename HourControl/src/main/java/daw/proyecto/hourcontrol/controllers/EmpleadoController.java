package daw.proyecto.hourcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import daw.proyecto.hourcontrol.Params;
import daw.proyecto.hourcontrol.domain.Empleado;
import daw.proyecto.hourcontrol.exceptions.NotFoundException;
import daw.proyecto.hourcontrol.repositories.EmpleadoRepository;
import daw.proyecto.hourcontrol.services.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @GetMapping({ "/", "" })
    public String showEmpleados(@RequestParam(required = false) Integer op,
            Model model) {
        if (op != null) {
            model.addAttribute("msg", Params.EMPLERR[op]);
        }
        model.addAttribute("listaEmpleados", empleadoService.obtenerTodos());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Empleado empleado = empleadoRepository.findByNombre(authentication.getName());
            model.addAttribute("idEmpleado", empleado.getId());
        }
        return "empleado/empleadoView";
    }

    @GetMapping("/nuevo")
    public String addEmpleado(Model model) {
        model.addAttribute("empleadoForm", new Empleado());
        return "empleado/newEmpleadoView";
    }

    @PostMapping("/nuevo/submit")
    public String addEmpleadoSubmit(@Valid Empleado empleadoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/empleados?op=2";
        }
        empleadoService.añadir(empleadoForm);
        return "redirect:/empleados?op=1";
    }

    @GetMapping("/editar/{id}")
    public String editEmpleado(@PathVariable Long id, Model model) {
        Empleado empleadoForm;
        try {
            empleadoForm = empleadoService.obtenerPorId(id);
        } catch (Exception e) {
            return "redirect:/empleados?op=3";
        }
        model.addAttribute("empleadoForm", empleadoForm);
        return "empleado/editEmpleadoView";
    }

    @PostMapping("/editar/submit")
    public String editEmpleadoSubmit(@Valid Empleado empleadoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/empleados?op=4";
        }
        try {
            empleadoService.editar(empleadoForm);
        } catch (NotFoundException e) {
            return "redirect:/empleados?op=3";
        }
        return "redirect:/empleados?op=5";
    }

    @GetMapping("/borrar/{id}")
    public String deleteEmpleado(@PathVariable Long id, Model model) throws NotFoundException {
        try {
            empleadoService.borrar(id);
        } catch (NotFoundException e) {
            return "redirect:/empleados?op=6";
        }
        return "redirect:/empleados?op=6";
    }

}


