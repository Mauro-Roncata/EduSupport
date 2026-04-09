package br.com.edusupport.edusupport.services;

import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.enums.Categoria;
import br.com.edusupport.edusupport.enums.Prioridade;
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
    @DisplayName("Lança erro ao tentar resolver chamado que não está em andamento")
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

    @Test
    @DisplayName("Deve resolver um chamado com sucesso quando estiver em andamento")
    void deveResolverChamadoComSucesso() {

        Long idDoChamado = 1L;
        Chamado chamadoEmAndamento = new Chamado();
        chamadoEmAndamento.setId(idDoChamado);
        chamadoEmAndamento.setStatus(StatusChamado.EM_ANDAMENTO);

        // Preenchendo dados mínimos
        chamadoEmAndamento.setTitulo("Roteador sem luz");
        chamadoEmAndamento.setDescricao("Luzes apagadas");
        chamadoEmAndamento.setCategoria(Categoria.REDE_INTERNET); // Usando um Enum real
        chamadoEmAndamento.setPrioridade(Prioridade.ALTA);

        // Quando buscar o ID 1, devolva o chamado que está em andamento
        when(chamadoRepository.findById(idDoChamado)).thenReturn(Optional.of(chamadoEmAndamento));

        // Ensinando o robô do Banco a salvar.
        when(chamadoRepository.save(any(Chamado.class))).thenAnswer(invocacao -> invocacao.getArgument(0));


        // Chamamando o método oficial. Ele deve processar tudo e nos devolver o DTO.
        var chamadoResolvido = chamadoService.resolverChamado(idDoChamado);


        assertNotNull(chamadoResolvido); // Garante que não voltou vazio
        assertEquals(StatusChamado.RESOLVIDO, chamadoResolvido.status()); // Garante que o Java alterou o status
        assertNotNull(chamadoResolvido.dataFechamento()); // Garante que o Java carimbou a data final

        // Confere se o robô do banco foi acionado EXATAMENTE 1 vez para salvar
        verify(chamadoRepository, times(1)).save(any(Chamado.class));
    }

    @Test
    @DisplayName("Deve assumir um chamado que está em aberto")
    void assumirEmAberto() {

        Long id = 1L;
        Chamado chamadoAberto = new Chamado();
        chamadoAberto.setId(id);
        chamadoAberto.setStatus(StatusChamado.ABERTO);

        chamadoAberto.setTitulo("Teste");
        chamadoAberto.setDescricao("Testes");
        chamadoAberto.setCategoria(Categoria.OUTROS);
        chamadoAberto.setPrioridade(Prioridade.BAIXA);

        when(chamadoRepository.findById(id)).thenReturn(Optional.of(chamadoAberto));
        when(chamadoRepository.save(any(Chamado.class))).thenAnswer(i -> i.getArgument(0)); // Efeito Espelho

        // 2. ACT
        var chamadoAssumido = chamadoService.atenderChamado(id);

        // 3. ASSERT
        assertNotNull(chamadoAssumido);
        assertEquals(StatusChamado.EM_ANDAMENTO, chamadoAssumido.status()); // Garante que o status mudou
        verify(chamadoRepository, times(1)).save(any(Chamado.class)); // Garante que mandou salvar
    }


    }

