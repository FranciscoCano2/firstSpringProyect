package com.egg.biblio.servicio;

import com.egg.biblio.entity.Editorial;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.repositorios.EditorialRepositorio;
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
public class EditorialService {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiExcepcion {

        validarNombre(nombre);

        Editorial editorial = new Editorial();

        editorial.setName(nombre);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarNombreEditorial(String idEditorial, String nombre) throws MiExcepcion {

        validarEditorial(idEditorial);
        validarNombre(nombre);

        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        if (respuestaEditorial.isPresent()) {
            Editorial editorialParaModificar = respuestaEditorial.get();

            editorialParaModificar.setName(nombre);
            editorialRepositorio.save(editorialParaModificar);
        }
    }

    public List<Editorial> listarEditorliales() {
        return editorialRepositorio.findAll();
    }

   public Editorial getOne(String id) {
        return editorialRepositorio.getOne(id);
    }



    private void validarEditorial(String idEditorial) throws MiExcepcion {
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiExcepcion("Id de editorial es nulo o vacio");
        }
    }

    private void validarNombre(String name) throws MiExcepcion {
        if (name == null || name.isEmpty()) {
            throw new MiExcepcion("El nombre de la editorial es nulo o vacio");
        }
    }

}
