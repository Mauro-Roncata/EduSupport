package br.com.edusupport.edusupport.dtos;

import br.com.edusupport.edusupport.enums.Categoria;
import br.com.edusupport.edusupport.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public record ChamadoRequestDTO(
        @NotBlank(message = "O título do chamado não pode estar vazio.")
        String titulo,

        @NotBlank(message = "A descrição do problema é obrigatória.")
        String descricao,

        @NotNull(message = "A categoria deve ser informada.")
        Categoria categoria,

        @NotNull(message = "A prioridade deve ser informada.")
        Prioridade prioridade,

        @AuthenticationPrincipal OAuth2User escola
) {
}