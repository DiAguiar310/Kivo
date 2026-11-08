package pt.kivo.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller // Indicar a Classe que controla os pedidos web
public class AdminPageController {

    // Metodo Incial que redireciona para o admin
    @GetMapping({
                    "/",
                    "/admin"
            })
    public String inicio(Principal principal) {

        // Caso tenha ja iniciado sessão entra logo no dashboard
        if (principal != null) {
            return "redirect:/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/login") // Mostra a pagina de login da pagina de administração
    public String login(Principal principal) {

        if (principal != null) {
            return "redirect:/dashboard";
        }

        return "admin/login";
    }

    @GetMapping("/dashboard") // Mostra a pagina do dashboard
    public String dashboard() {
        return "admin/dashboard";
    }


    @GetMapping("/suporte")
    public String suporte() {
        return "admin/suporte";
    }


}


