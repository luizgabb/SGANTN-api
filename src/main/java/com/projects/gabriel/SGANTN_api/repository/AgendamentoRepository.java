package com.projects.gabriel.SGANTN_api.repository;

import com.projects.gabriel.SGANTN_api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByClienteId(Long clienteId);

    List<Agendamento> findByPrestadorId(Long prestadorId);

}
