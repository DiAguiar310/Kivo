package pt.kivo.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Significa que esta classe representa uma tabela
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gerar o ID
    private Long id;

    private String nome; // Nome da categoria

    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
