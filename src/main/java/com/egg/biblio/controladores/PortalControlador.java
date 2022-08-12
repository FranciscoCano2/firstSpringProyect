package com.egg.biblio.controladores;

import com.egg.biblio.entity.Usuario;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.servicio.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CanoFrancisco
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "inicio.html";
    }

    @GetMapping("/registrar")
    public String registrarUsuario() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registroUsuario(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            usuarioServicio.crearUsuario(archivo, nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado exitosamente");
            return "index.html";
        } catch (MiExcepcion e) {

            modelo.put("Error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";
        }
    }

    @GetMapping("/login")
    public String logUsuario() {
        return "login.html";
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("Error", "Usuario o Contraseña invalida");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admins/dashboard";
        }

        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizarUsuario(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, @RequestParam ModelMap modelo) throws MiExcepcion {
        try {
            usuarioServicio.actualizarUsuario(archivo, password2, nombre, email, password, password2);

            modelo.put("exito", "Usuario Actualizado Correctamente");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

        }
        return "usuario_ modificar";

    }
}
