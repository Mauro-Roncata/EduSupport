package br.com.edusupport.edusupport.controllers;


import br.com.edusupport.edusupport.entities.Chamado;
import br.com.edusupport.edusupport.services.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping
    public ResponseEntity<Chamado> criarChamado(@Valid @RequestBody Chamado chamado){

        Chamado chamado1 = chamadoService.abrirChamado(chamado);
        return ResponseEntity.ok().body(chamado1);
    }
}
