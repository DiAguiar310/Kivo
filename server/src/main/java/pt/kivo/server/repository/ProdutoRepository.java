package pt.kivo.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.kivo.server.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
