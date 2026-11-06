package pt.kivo.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import pt.kivo.server.model.Produto;
import pt.kivo.server.model.Subcategoria;
import pt.kivo.server.repository.ProdutoRepository;
import pt.kivo.server.repository.SubcategoriaRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final SubcategoriaRepository subcategoriaRepository;

    public ProdutoController(
            ProdutoRepository produtoRepository,
            SubcategoriaRepository subcategoriaRepository) {

        this.produtoRepository = produtoRepository;
        this.subcategoriaRepository = subcategoriaRepository;
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produto obter(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"
                ));
    }

    @PostMapping
    public Produto criar(@RequestBody Produto dados) {

        Produto produto = new Produto();

        produto.setNome(dados.getNome());
        produto.setMarca(dados.getMarca());

        produto.setSubcategorias(
                procurarSubcategorias(dados.getSubcategorias())
        );

        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(
            @PathVariable Long id,
            @RequestBody Produto dados) {

        Produto produto = obter(id);

        produto.setNome(dados.getNome());
        produto.setMarca(dados.getMarca());

        produto.setSubcategorias(
                procurarSubcategorias(dados.getSubcategorias())
        );

        return produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable Long id) {

        Produto produto = obter(id);
        produtoRepository.delete(produto);
    }

    private Set<Subcategoria> procurarSubcategorias(
            Set<Subcategoria> recebidas) {

        if (recebidas == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O campo subcategorias é obrigatório"
            );
        }

        Set<Long> ids = new HashSet<>();

        for (Subcategoria subcategoria : recebidas) {
            if (subcategoria.getId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Cada subcategoria precisa de um ID"
                );
            }

            ids.add(subcategoria.getId());
        }

        List<Subcategoria> encontradas =
                subcategoriaRepository.findAllById(ids);

        if (encontradas.size() != ids.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Uma ou mais subcategorias não existem"
            );
        }

        return new HashSet<>(encontradas);
    }
}
