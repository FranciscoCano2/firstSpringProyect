package com.egg.biblio.servicio;

import com.egg.biblio.entity.Autor;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.repositorios.AutorRepositorio;
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
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void createAutor(String name) throws MiExcepcion {

        validarNombre(name);

        Autor autor = new Autor();

        autor.setName(name);

        autorRepositorio.save(autor);

    }

    @Transactional
    public void modificarNombreAutor(String idAutor, String nombre) throws MiExcepcion {

        validarAutor(idAutor);
        validarNombre(nombre);

        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);

        if (respuestaAutor.isPresent()) {
            Autor autorParaModificar = respuestaAutor.get();

            autorParaModificar.setName(nombre);
            autorRepositorio.save(autorParaModificar);
        }
    }

    public Autor getOne(String id) {
        return autorRepositorio.getOne(id);
    }

    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }

    private void validarAutor(String idAutor) throws MiExcepcion {
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiExcepcion("Id de autor es nulo o vacio");
        }
    }

    private void validarNombre(String name) throws MiExcepcion {
        if (name == null || name.isEmpty()) {
            throw new MiExcepcion("El nombre del autor es nulo o vacio");
        }
    }

}
