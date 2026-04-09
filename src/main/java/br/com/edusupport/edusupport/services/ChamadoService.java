package br.com.edusupport.edusupport.services;

import br.com.edusupport.edusupport.dtos.ChamadoRequestDTO;
import br.com.edusupport.edusupport.dtos.ChamadoResponseDTO;
import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.enums.StatusChamado;
import br.com.edusupport.edusupport.repositories.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final EmailService emailService;

    public ChamadoResponseDTO abrirChamado(ChamadoRequestDTO dto) {

        Chamado chamado = new Chamado();

        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        chamado.setCategoria(dto.categoria());
        chamado.setPrioridade(dto.prioridade());
        chamado.setStatus(StatusChamado.ABERTO);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        emailService.enviarNotificacaoNovoChamado(chamadoSalvo);

        return new ChamadoResponseDTO(chamadoSalvo.getId(),chamadoSalvo.getTitulo(),chamadoSalvo.getDescricao(),
                chamadoSalvo.getStatus(),chamadoSalvo.getCategoria(),
                chamadoSalvo.getPrioridade(),chamadoSalvo.getDataAbertura(),
                chamadoSalvo.getDataFechamento());
    }

    public Page<ChamadoResponseDTO> listAll(Pageable pageable) {
        return chamadoRepository.findAll(pageable)
                .map(chamado -> new ChamadoResponseDTO(
                        chamado.getId(),
                        chamado.getTitulo(),
                        chamado.getDescricao(),
                        chamado.getStatus(),
                        chamado.getCategoria(),
                        chamado.getPrioridade(),
                        chamado.getDataAbertura(),
                        chamado.getDataFechamento()
                ));
        };


    // Método para assumir chamado
    public ChamadoResponseDTO atenderChamado(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        if (chamado.getStatus() != StatusChamado.ABERTO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apenas chamados abertos podem ser assumidos");
        }

        chamado.setStatus(StatusChamado.EM_ANDAMENTO);
        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        return new ChamadoResponseDTO(
                chamadoSalvo.getId(),
                chamadoSalvo.getTitulo(),
                chamadoSalvo.getDescricao(),
                chamadoSalvo.getStatus(),
                chamadoSalvo.getCategoria(),
                chamadoSalvo.getPrioridade(),
                chamadoSalvo.getDataAbertura(),
                chamadoSalvo.getDataFechamento()
        );
    }

    // Método para finalizar o serviço
    public ChamadoResponseDTO resolverChamado(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        if (chamado.getStatus() == StatusChamado.EM_ANDAMENTO) {
            chamado.setStatus(StatusChamado.RESOLVIDO);
            chamado.setDataFechamento(LocalDateTime.now());
            Chamado chamadoSalvo = chamadoRepository.save(chamado);
            return new ChamadoResponseDTO(
                    chamadoSalvo.getId(),
                    chamadoSalvo.getTitulo(),
                    chamadoSalvo.getDescricao(),
                    chamadoSalvo.getStatus(),
                    chamadoSalvo.getCategoria(),
                    chamadoSalvo.getPrioridade(),
                    chamadoSalvo.getDataAbertura(),
                    chamadoSalvo.getDataFechamento()
            );
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O chamado não pode ser fechado pois não está Em Andamento.");
    }
}


