package br.com.edusupport.edusupport.services;

import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.enums.StatusChamado;
import br.com.edusupport.edusupport.repositories.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
