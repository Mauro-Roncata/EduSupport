package br.com.edusupport.edusupport.controllers;


import br.com.edusupport.edusupport.dtos.ChamadoRequestDTO;
import br.com.edusupport.edusupport.dtos.ChamadoResponseDTO;
import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.services.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criarChamado(@Valid @RequestBody ChamadoRequestDTO dto){

        ChamadoResponseDTO chamadoCriado = chamadoService.abrirChamado(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoCriado);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarChamado(){
        List<ChamadoResponseDTO> chamadoList = chamadoService.listAll();
        return ResponseEntity.ok(chamadoList);
    }

    @PutMapping("/{id}/atender")
    public ResponseEntity<Chamado> atenderChamado(@PathVariable Long id) {

        Chamado chamadoAtualizado = chamadoService.atenderChamado(id);
        return ResponseEntity.ok().body(chamadoAtualizado);
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<Chamado> resolverChamado(@PathVariable Long id){

        Chamado chamadoResolvido = chamadoService.resolverChamado(id);
        return ResponseEntity.ok().body(chamadoResolvido);

    }



}
