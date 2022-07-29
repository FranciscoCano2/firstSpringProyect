package com.egg.biblio.repositorios;

import com.egg.biblio.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CanoFrancisco
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {


}
