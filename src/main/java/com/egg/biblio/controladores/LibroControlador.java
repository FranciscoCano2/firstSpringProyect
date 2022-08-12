package com.egg.biblio.controladores;

import com.egg.biblio.entity.Autor;
import com.egg.biblio.entity.Book;
import com.egg.biblio.entity.Editorial;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.servicio.AutorServicio;
import com.egg.biblio.servicio.EditorialService;
import com.egg.biblio.servicio.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping("/libros")
public class LibroControlador {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("")
    public String libroInicio() {
        return "libro.html";
    }

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

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Book> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificarLibro(@PathVariable Long isbn, ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditorliales();

        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("autores", autores);
        modelo.put("libro", libroServicio.getOne(isbn));

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificarLibro(@PathVariable Long isbn, String titulo, Integer ejemplares,
            String idAutor, String idEditorial, ModelMap modelo) {

        try {
            libroServicio.modificarEjemplaresLibro(isbn, ejemplares);
            libroServicio.modificarTituloLibro(isbn, titulo);
            libroServicio.modificarAutorLibro(isbn, idAutor);
            libroServicio.modificarEditorialLibro(isbn, idEditorial);

            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "libro_modificar.html";
        }

    }
}
