package pt.kivo.server.model;

import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String marca;

    @ManyToMany // Relação muitos para muitos

    // Criar a Tabela SQL para guardar as subcategorias acossiadas a um produto
    @JoinTable(name = "produto_subcategoria", // Nome da Tabela no SQL
            joinColumns = @JoinColumn(name = "produto_id"), // Coluna que aponta para o produto
            inverseJoinColumns = @JoinColumn(name = "subcategoria_id") // Coluna que aponta para  a subcategoria
    )
    private Set<Subcategoria> subcategorias = new HashSet<>(); // Permitir criar varias subcategorias sem repetir o mesmo elemento

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Set<Subcategoria> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(Set<Subcategoria> subcategorias) {
        this.subcategorias = subcategorias;
    }
}
