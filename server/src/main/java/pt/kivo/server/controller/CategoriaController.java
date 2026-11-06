package pt.kivo.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.kivo.server.model.Categoria;
import pt.kivo.server.repository.CategoriaRepository;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) { this.categoriaRepository = categoriaRepository; }

    @GetMapping
    public List<Categoria> listar() { return this.categoriaRepository.findAll(); }





}
