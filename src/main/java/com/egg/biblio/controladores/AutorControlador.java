package com.egg.biblio.controladores;

import com.egg.biblio.entity.Autor;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.servicio.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author CanoFrancisco
 */
@Controller
@RequestMapping("/autores")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("")
    public String autor() {
        return "autor.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String name) {

        try {
            autorServicio.createAutor(name);
        } catch (MiExcepcion ex) {
           // Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autor_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        return "lista_autores.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", autorServicio.getOne(id));

        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarAutor(@PathVariable String id, String name, ModelMap modelo) {

        try {
            autorServicio.modificarNombreAutor(id, name);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "autor_modificar.html";
        }

    }

}
