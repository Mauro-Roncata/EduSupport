package br.com.edusupport.edusupport.services;

import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.enums.StatusChamado;
import br.com.edusupport.edusupport.repositories.ChamadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) //Liga o mockito

public class ChamadoServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;
    @InjectMocks
    private ChamadoService chamadoService;

    @Test
    @DisplayName("Lança erro ao tentar resolver chamdo que não está em andamento")
    void naoResolveChamadoForaDeAndamento (){
        Long idChamado = 1L;
        Chamado chamadoFalso = new Chamado();
        chamadoFalso.setId(idChamado);
        chamadoFalso.setStatus(StatusChamado.ABERTO);

        //Quando busca o chamado por id, retorna o chamado falso
        when(chamadoRepository.findById(idChamado)).thenReturn(Optional.of(chamadoFalso));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()-> {
            chamadoService.resolverChamado(idChamado);
        } );

        // Verifica se o erro tem status 400
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Garante que o duble do banco de dados não salve nada
    verify(chamadoRepository, never()).save(any(Chamado.class));




    }
}
