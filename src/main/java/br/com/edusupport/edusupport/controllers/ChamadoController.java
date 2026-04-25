package br.com.edusupport.edusupport.controllers;


import br.com.edusupport.edusupport.dtos.ChamadoRequestDTO;
import br.com.edusupport.edusupport.dtos.ChamadoResponseDTO;
import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.services.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criarChamado(@Valid @RequestBody ChamadoRequestDTO dto, @AuthenticationPrincipal OAuth2User principal){

        String emailLogado = principal.getAttribute("email");

        ChamadoResponseDTO chamadoCriado = chamadoService.abrirChamado(dto, emailLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoCriado);
    }


    @GetMapping
    public ResponseEntity<Page<ChamadoResponseDTO>> listarChamado(@ParameterObject @PageableDefault(size = 10, sort = {"dataAbertura"})Pageable pageable){
        Page<ChamadoResponseDTO> chamadoList = chamadoService.listAll(pageable);
        return ResponseEntity.ok(chamadoList);
    }

    @PutMapping("/{id}/atender")
    public ResponseEntity<ChamadoResponseDTO> atenderChamado(@PathVariable Long id) {

        ChamadoResponseDTO chamadoAtualizado = chamadoService.atenderChamado(id);
        return ResponseEntity.ok(chamadoAtualizado);
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<ChamadoResponseDTO> resolverChamado(@PathVariable Long id){

        ChamadoResponseDTO chamadoResolvido = chamadoService.resolverChamado(id);
        return ResponseEntity.ok().body(chamadoResolvido);

    }




}
