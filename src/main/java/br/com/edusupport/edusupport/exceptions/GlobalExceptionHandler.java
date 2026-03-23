package br.com.edusupport.edusupport.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        // JSON customizado, limpo e direto
        Map<String, Object> corpoDoErro = new LinkedHashMap<>();
        corpoDoErro.put("timestamp", LocalDateTime.now());
        corpoDoErro.put("status", ex.getStatusCode().value());
        corpoDoErro.put("mensagem", ex.getReason());

        return ResponseEntity.status(ex.getStatusCode()).body(corpoDoErro);
    }

}
