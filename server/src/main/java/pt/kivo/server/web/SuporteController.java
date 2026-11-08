package pt.kivo.server.web;

import pt.kivo.server.service.SuporteEmailService;

import jakarta.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class SuporteController {

    private final SuporteEmailService suporteEmailService;

    public SuporteController(SuporteEmailService suporteEmailService) {
        this.suporteEmailService = suporteEmailService;
    }

    @PostMapping("/suporte")
    public String enviarPedido(
            @RequestParam String loja,
            @RequestParam String assunto,
            @RequestParam String tipo,
            @RequestParam String urgencia,
            @RequestParam String descricao,
            @RequestParam(required = false) MultipartFile[] imagens,
            RedirectAttributes redirectAttributes) {

        try {
            // Envia o pedido para o email de suporte
            suporteEmailService.enviarPedido(
                    loja, assunto, tipo, urgencia, descricao, imagens
            );

            // Mensagem apresentada quando o envio é bem-sucedido
            redirectAttributes.addFlashAttribute(
                    "sucesso", "Pedido de suporte enviado com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            // Dados ou anexos inválidos
            redirectAttributes.addFlashAttribute("erro", e.getMessage());

        } catch (MessagingException | IOException | MailException e) {

            // Falha ao preparar ou enviar o email
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível enviar o pedido. Tenta novamente."
            );

        }

        return "redirect:/suporte";
    }
}
