package br.com.edusupport.edusupport.dtos;

import br.com.edusupport.edusupport.enums.Categoria;
import br.com.edusupport.edusupport.enums.Prioridade;
import br.com.edusupport.edusupport.enums.StatusChamado;

import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        StatusChamado status,
        Categoria categoria,
        Prioridade prioridade,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento
) {
}
