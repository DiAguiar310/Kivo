package pt.kivo.server.controller;

import org.apache.catalina.core.PropertiesRoleMappingListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.kivo.server.model.Produto;
import pt.kivo.server.repository.ProdutoRepository;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @GetMapping("/{id}") //Permite consultar o produto pelo ID
    public Produto obter(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"
                ));
    }

    @PutMapping("/{id}")  //Consulta o produto na base de dados, e guarda as informações na base de dados
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto dados) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"
                ));

        produto.setNome(dados.getNome());
        produto.setMarca(dados.getMarca());

        return produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}") //Apagar um produto
    public void apagar(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Produto não encontrado"
            );
        }
        produtoRepository.deleteById(id);
    }





}

