package br.com.edusupport.edusupport.entities;

import br.com.edusupport.edusupport.enums.Categoria;
import br.com.edusupport.edusupport.enums.Prioridade;
import br.com.edusupport.edusupport.enums.StatusChamado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "tb_chamado")
@Getter
@Setter
@NoArgsConstructor
public class Chamado {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;
    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusChamado status;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private String escola;

    public Chamado(String titulo, String descricao, Prioridade prioridade, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = StatusChamado.ABERTO;
    }

    @PrePersist
    public void prePersist () {
        this.dataAbertura = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chamado chamado = (Chamado) o;
        return Objects.equals(id, chamado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
