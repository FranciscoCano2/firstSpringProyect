package com.egg.biblio.repositorios;

import com.egg.biblio.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author franc
 */

public interface FotoRepositorio extends JpaRepository<Foto, String>{
    
}
