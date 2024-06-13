package daw.proyecto.hourcontrol.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import daw.proyecto.hourcontrol.domain.Empleado;
import daw.proyecto.hourcontrol.repositories.EmpleadoRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByNombre(username);
        if (empleado == null)
            throw (new UsernameNotFoundException("Empleado no encontrado!"));
        return User // org.springframework.security.core.userdetails.User
                .withUsername(username)
                .roles(empleado.getRol().toString())
                .password(empleado.getClave())
                .build();
    }
}
