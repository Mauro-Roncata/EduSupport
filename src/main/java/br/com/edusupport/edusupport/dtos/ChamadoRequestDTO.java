package br.com.edusupport.edusupport.dtos;

import br.com.edusupport.edusupport.enums.Categoria;
import br.com.edusupport.edusupport.enums.Prioridade;

public record ChamadoRequestDTO(
        String titulo,
        String descricao,
        Categoria categoria,
        Prioridade prioridade
) {
}
