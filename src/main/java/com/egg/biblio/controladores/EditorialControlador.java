package com.egg.biblio.controladores;


import com.egg.biblio.entity.Editorial;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.servicio.EditorialService;
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
@RequestMapping("/editoriales")
public class EditorialControlador {

    @Autowired
    private EditorialService editorialService;

    @GetMapping("")
    public String editorialInicio() {
        return "editorial.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre) {
        System.out.println(nombre);
        try {
            editorialService.crearEditorial(nombre);
        } catch (MiExcepcion ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "editorial_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialService.listarEditorliales();
        return "lista_editoriales.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial", editorialService.getOne(id));

        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarAutor(@PathVariable String id, String name, ModelMap modelo) {

        try {
            editorialService.modificarNombreEditorial(id, name);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "editorial_modificar.html";
        }

    }
}
