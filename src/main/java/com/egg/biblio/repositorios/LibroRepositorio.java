package com.egg.biblio.repositorios;

import com.egg.biblio.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CanoFrancisco
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Book, Long> {

    @Query("SELECT l FROM Book l WHERE l.titulo = :titulo")
    public Book buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Book l WHERE l.autor.name = :name")
    public List<Book> buscarPorAutor(@Param("name") String name);

    }
