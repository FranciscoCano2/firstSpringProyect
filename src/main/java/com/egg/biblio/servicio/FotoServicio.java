package com.egg.biblio.servicio;

import com.egg.biblio.entity.Foto;
import com.egg.biblio.excepciones.MiExcepcion;
import com.egg.biblio.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CanoFrancisco
 */
@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardarFoto(MultipartFile archivo) throws MiExcepcion {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public Foto actualizarFoto(MultipartFile archivo, String idImagen) throws MiExcepcion {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idImagen != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }

                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }

    }

}
