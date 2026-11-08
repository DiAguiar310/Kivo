package pt.kivo.server.repository;

import pt.kivo.server.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {

    // Procura um utilizador através do email
    Optional<Utilizador> findByEmail(String email);

}
