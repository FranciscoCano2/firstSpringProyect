package com.egg.biblio.servicio;

import com.egg.biblio.entity.Usuario;
import com.egg.biblio.enumeraciones.Rol;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author CanoFrancisco
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void crearUsuario(String nombre, String email, String password, String password2) throws MiExcepcion {
        
        validarNombre(nombre);
        validarEmail(email);
        validarPassword(password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setName(nombre);
        usuario.setEmail(email); 
        
        usuario.setAlta(new Date());
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuario.setRol(Rol.USER);
        
        usuarioRepositorio.save(usuario);
    }
    
    private void validarNombre(String nombre) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("Nombre nulo o vacio");
        }
    }
    
    private void validarEmail(String email) throws MiExcepcion {
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("Email nulo o vacio");
        }
    }
    
    private void validarPassword(String password, String password2) throws MiExcepcion {
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiExcepcion("La contraseña debe tener mas de 5 digitos");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas no coinciden");
        }
        
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession sesion = attr.getRequest().getSession(true);
            
            sesion.setAttribute("usuarioSession", usuario);
            
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }
    
}
