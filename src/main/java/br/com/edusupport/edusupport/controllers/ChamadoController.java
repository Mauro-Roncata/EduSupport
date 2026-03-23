package br.com.edusupport.edusupport.controllers;


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
    public ResponseEntity<Chamado> criarChamado(@Valid @RequestBody Chamado chamado){

        Chamado chamado1 = chamadoService.abrirChamado(chamado);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamado1);
    }

    @GetMapping
    public ResponseEntity<List<Chamado>> listarChamado(){
        List<Chamado> chamadoList = chamadoService.listAll();
        return ResponseEntity.ok().body(chamadoList);
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
