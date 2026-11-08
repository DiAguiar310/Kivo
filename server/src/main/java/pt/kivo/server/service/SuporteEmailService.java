package pt.kivo.server.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SuporteEmailService {

    private final JavaMailSender mailSender;
    private final String remetente;

    public SuporteEmailService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}") String remetente) {

        this.mailSender = mailSender;
        this.remetente = remetente;
    }

    public void enviarPedido(
            String loja,
            String assunto,
            String tipo,
            String urgencia,
            String descricao,
            MultipartFile[] imagens) throws MessagingException, IOException {


        if (!java.util.Set.of("BAIXA", "MEDIA", "ALTA", "CRITICA").contains(urgencia)) {
            throw new IllegalArgumentException("Grau de urgência inválido.");
        }

        String emojiUrgencia = switch (urgencia) {
            case "BAIXA" -> "🟢";
            case "MEDIA" -> "🟡";
            case "ALTA" -> "🟠";
            case "CRITICA" -> "🔴";
            default -> "⚪";
        };

        // Criar o email
        MimeMessage mensagem = mailSender.createMimeMessage();

        // O true permite adicionar anexos
        MimeMessageHelper email = new MimeMessageHelper(mensagem, true, "UTF-8");

        email.setFrom(remetente);
        email.setTo("suportekivo@proton.me");
        email.setSubject("[Kivo Suporte] " + emojiUrgencia + " [" + urgencia + "] " + assunto);
        // Conteúdo do email
        String emailLoja = "lojateste@kivo.pt"; // Temporário: só tenho a Loja de Teste ;)

        String texto = "Loja: " + loja
                + "\nTipo de pedido: " + tipo
                + "\nAssunto: " + assunto
                + "\n\nDescrição:\n" + descricao
                + "\nGrau de urgência: " + emojiUrgencia + " " + urgencia

                + "\n\n----------------------------------------"
                + "\nNOTA:"
                + "\nEste é um email automático enviado pelo sistema Kivo."
                + "\nPor favor, não responda diretamente a esta mensagem."
                + "\n\nPara contactar a loja responsável pelo pedido,"
                + "\nutilize o endereço de email da respetiva loja."
                + "\nFormato: loja[localização]@kivo.pt"
                + "\nContacto desta loja: " + emailLoja;


        email.setText(texto);

        // Adicionar as imagens, se existirem
        int contador = 0;

        if (imagens != null) {
            for (MultipartFile imagem : imagens) {

                if (imagem.isEmpty()) {
                    continue;
                }
                contador++;

                // Máximo de 3 imagens
                if (contador > 3) {
                    throw new IllegalArgumentException("Máximo de 3 imagens.");
                }

                // Máximo de 5 MB por imagem
                if (imagem.getSize() > 5 * 1024 * 1024) {
                    throw new IllegalArgumentException("Imagem demasiado grande.");
                }

                // Verificar o formato declarado da imagem
                String tipoImagem = imagem.getContentType();

                String extensao;

                if ("image/png".equals(tipoImagem)) {
                    extensao = ".png";
                } else if ("image/jpeg".equals(tipoImagem)) {
                    extensao = ".jpg";
                } else if ("image/webp".equals(tipoImagem)) {
                    extensao = ".webp";
                } else {
                    throw new IllegalArgumentException("Formato de imagem inválido.");
                }

                // Anexar a imagem ao email
                email.addAttachment(
                        "imagem-" + contador + extensao,
                        new ByteArrayResource(imagem.getBytes()),
                        tipoImagem
                );
            }
        }

        // Enviar o email
        mailSender.send(mensagem);
    }
}
