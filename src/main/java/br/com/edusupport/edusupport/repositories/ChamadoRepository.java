package br.com.edusupport.edusupport.repositories;

import br.com.edusupport.edusupport.entities.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
