package pt.kivo.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.kivo.server.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
    