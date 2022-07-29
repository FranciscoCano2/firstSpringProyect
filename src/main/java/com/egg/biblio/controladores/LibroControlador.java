package com.egg.biblio.controladores;

import com.egg.biblio.entity.Autor;
import com.egg.biblio.entity.Editorial;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.servicio.AutorServicio;
import com.egg.biblio.servicio.EditorialService;
import com.egg.biblio.servicio.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author CanoFrancisco
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditorliales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registroLibro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        try {
            libroServicio.createBook(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "Libro cargado correctamente");
        } catch (MiExcepcion e) {

            modelo.put("error", e.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }

}
