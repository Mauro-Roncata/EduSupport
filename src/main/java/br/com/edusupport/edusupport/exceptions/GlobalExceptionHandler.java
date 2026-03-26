package br.com.edusupport.edusupport.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();

        List<DadosErroValidacao> listaDeErros = erros.stream()
                .map(DadosErroValidacao::new)
                .toList();

        return ResponseEntity.badRequest().body(listaDeErros);
    }

    public record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
