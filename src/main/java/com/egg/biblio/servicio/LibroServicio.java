package com.egg.biblio.servicio;

import com.egg.biblio.entity.Autor;
import com.egg.biblio.entity.Book;
import com.egg.biblio.entity.Editorial;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.repositorios.AutorRepositorio;
import com.egg.biblio.repositorios.EditorialRepositorio;
import com.egg.biblio.repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CanoFrancisco
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void createBook(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion {

        validarISBN(isbn);
        validarTitulo(titulo);
        validarEjemplares(ejemplares);
        validarAutor(idAutor);
        validarEditorial(idEditorial);

        Book newBook = new Book();

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        newBook.setIsbn(isbn);
        newBook.setTitulo(titulo);
        newBook.setEjemplares(ejemplares);

        newBook.setAlta(new Date());

        newBook.setAutor(autor);
        newBook.setEditorial(editorial);

        libroRepositorio.save(newBook);
    }

    public List<Book> listarLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional
    public void modificarTituloLibro(Long isbn, String titulo) throws MiExcepcion {

        validarISBN(isbn);
        validarTitulo(titulo);

        Optional<Book> respuestaLibro = libroRepositorio.findById(isbn);

        if (respuestaLibro.isPresent()) {
            Book libroParaModificar = respuestaLibro.get();

            libroParaModificar.setTitulo(titulo);
            libroRepositorio.save(libroParaModificar);
        }
    }

    @Transactional
    public void modificarEjemplaresLibro(Long isbn, Integer ejemplares) throws MiExcepcion {

        validarISBN(isbn);
        validarEjemplares(ejemplares);

        Optional<Book> respuestaLibro = libroRepositorio.findById(isbn);

        if (respuestaLibro.isPresent()) {
            Book libroParaModificar = respuestaLibro.get();

            libroParaModificar.setEjemplares(ejemplares);

            libroRepositorio.save(libroParaModificar);
        }
    }

    @Transactional
    public void modificarAutorLibro(Long isbn, String idAutor) throws MiExcepcion {

        validarISBN(isbn);
        validarAutor(idAutor);

        Optional<Book> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);

        if (respuestaLibro.isPresent() && respuestaAutor.isPresent()) {
            Book libroParaModificar = respuestaLibro.get();

            libroParaModificar.setAutor(respuestaAutor.get());

            libroRepositorio.save(libroParaModificar);
        }
    }

    @Transactional
    public void modificarEditorialLibro(Long isbn, String idEditorial) throws MiExcepcion {

        validarISBN(isbn);
        validarEditorial(idEditorial);

        Optional<Book> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        if (respuestaLibro.isPresent() && respuestaEditorial.isPresent()) {
            Book libroParaModificar = respuestaLibro.get();

            libroParaModificar.setEditorial(respuestaEditorial.get());

            libroRepositorio.save(libroParaModificar);
        }
    }

    public Book getOne(Long isbn) {
        return libroRepositorio.getOne(isbn);
    }

    private void validarISBN(Long isbn) throws MiExcepcion {
        if (isbn == null) {
            throw new MiExcepcion("ISBN nulo");
        }
    }

    private void validarTitulo(String titulo) throws MiExcepcion {
        if (titulo == null || titulo.isEmpty()) {
            throw new MiExcepcion("Titulo nulo o vacio");
        }
    }

    private void validarEjemplares(Integer ejemplares) throws MiExcepcion {
        if (ejemplares == null) {
            throw new MiExcepcion("Cantidad de ejemplares nula");
        }
    }

    private void validarAutor(String idAutor) throws MiExcepcion {
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiExcepcion("Id de autor nulo o vacio");
        }
    }

    private void validarEditorial(String idEditorial) throws MiExcepcion {
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiExcepcion("Id de la editorial nula o vacia");
        }
    }

}
