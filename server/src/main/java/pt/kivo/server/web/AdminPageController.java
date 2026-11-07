package pt.kivo.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Indicar a Classe que controla os pedidos web
public class AdminPageController {

    // Metodo Incial que redireciona para o admin
    @GetMapping({
                    "/",
                    "/admin"
            })
    public String inicio() {
        return "redirect:/login";
    }

    @GetMapping("/login") // Mostra a pagina de login da pagina de administração
    public String login() {
        return "admin/login";
    }

    @GetMapping("/dashboard") // Mostra a pagina do dashboard
    public String dashboard() {
        return "admin/dashboard";
    }

}


