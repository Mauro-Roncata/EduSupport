package br.com.edusupport.edusupport.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @GetMapping("/me")
    public Map<String, Object> usuarioLogado(@AuthenticationPrincipal OAuth2User principal){
        Map<String, Object> dadosUsuario = new HashMap<>();

        if (principal == null) {
            return dadosUsuario;
        }

        String nome = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String foto = principal.getAttribute("picture");

        dadosUsuario.put("nome", nome);
        dadosUsuario.put("email", email);
        dadosUsuario.put("foto",foto);


        if (email != null && email.equals("mauro.r@smed.ijui.rs.gov.br")) {
            dadosUsuario.put("perfil", "TI");
        } else {
            dadosUsuario.put("perfil", "SECRETARIA");
        }

        return dadosUsuario;
    }
}
