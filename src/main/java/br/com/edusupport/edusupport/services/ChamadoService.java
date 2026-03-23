package br.com.edusupport.edusupport.services;

import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.enums.StatusChamado;
import br.com.edusupport.edusupport.repositories.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public Chamado abrirChamado(Chamado chamado) {
        chamado.setStatus(StatusChamado.ABERTO);

        return chamadoRepository.save(chamado);
    }

    public List<Chamado> listAll() {
        return chamadoRepository.findAll();
    }

    // Método para assumir chamado
    public Chamado atenderChamado(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        chamado.setStatus(StatusChamado.EM_ANDAMENTO);
        return chamadoRepository.save(chamado);
    }

    // Método para finalizar o serviço
    public Chamado resolverChamado(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        chamado.setStatus(StatusChamado.RESOLVIDO);
        chamado.setDataFechamento(LocalDateTime.now());
        return chamadoRepository.save(chamado);
    }

}
