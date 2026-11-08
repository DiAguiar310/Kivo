package pt.kivo.server.service;

import pt.kivo.server.model.Utilizador;
import pt.kivo.server.repository.UtilizadorRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KivoUserDetailsService implements UserDetailsService {

    private final UtilizadorRepository utilizadorRepository;

    // Construtor
    public KivoUserDetailsService(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        // Procura o utilizador na base de dados
        Utilizador utilizador = utilizadorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilizador não encontrado"
                ));

        // Converte o Utilizador em um utilizador do Spring Security
        return User.withUsername(utilizador.getEmail())
                .password(utilizador.getPasswordHash())
                .roles(utilizador.getPerfil())
                .build();
    }
}
