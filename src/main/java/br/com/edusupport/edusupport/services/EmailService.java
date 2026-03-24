package br.com.edusupport.edusupport.services;

import br.com.edusupport.edusupport.entities.Chamado;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarNotificacaoNovoChamado(Chamado chamado){
        SimpleMailMessage mensagem = new SimpleMailMessage();

        // Como é um teste, remetentes e detinatarios ficticios
        mensagem.setFrom("sistema@edusupport.com.br");
        mensagem.setTo("suporte.educacao@ijui.rs.gov.br"); // O email da equipe de TI

        mensagem.setSubject("NOVO CHAMADO ABERTO: #" + chamado.getId() + " - " + chamado.getTitulo());

        String corpoDoEmail = """
                Olá Equipe de Suporte,
                
                Um novo chamado foi registrado no EduSupport e precisa de atenção.
                
                Detalhes do Chamado:
                ID: %d
                Categoria: %s
                Prioridade: %s
                Descrição: %s
                
                Acesse o painel para iniciar o atendimento.
                """.formatted(
                chamado.getId(),
                chamado.getCategoria(),
                chamado.getPrioridade(),
                chamado.getDescricao()
        );

        mensagem.setText(corpoDoEmail);

        // Manda o email
        mailSender.send(mensagem);
    }
}


