package com.egg.biblio.servicio;

import com.egg.biblio.entity.Foto;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.repositorios.FotoRepositorio;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CanoFrancisco
 */
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardarFoto(MultipartFile archivo) throws MiExcepcion {

        try {
            Foto foto = new Foto();
            foto.setMime(archivo.getContentType());
            foto.setNombre(archivo.getName());
            foto.setContenido(archivo.getBytes());

            return fotoRepositorio.save(foto);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
