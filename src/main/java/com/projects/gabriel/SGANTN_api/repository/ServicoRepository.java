package com.projects.gabriel.SGANTN_api.repository;

import com.projects.gabriel.SGANTN_api.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
