package com.egg.biblio.repositorios;

import com.egg.biblio.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CanoFrancisco
 */
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {


}

