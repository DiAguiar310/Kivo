package pt.kivo.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.kivo.server.model.Subcategoria;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
}
