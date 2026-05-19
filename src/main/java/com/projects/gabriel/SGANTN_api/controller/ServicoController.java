package com.projects.gabriel.SGANTN_api.controller;

import com.projects.gabriel.SGANTN_api.model.Servico;
import com.projects.gabriel.SGANTN_api.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;


    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody Servico servico) {
        if (servico.getPreco() == null || servico.getPreco().doubleValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço do serviço não pode ser negativo.");
        }
        Servico servicoSalvo = servicoRepository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos = servicoRepository.findAll();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado."));
        return ResponseEntity.ok(servico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado para atualização."));

        servicoExistente.setNome(servicoAtualizado.getNome());
        servicoExistente.setPreco(servicoAtualizado.getPreco());
        servicoExistente.setDuracaoMinutos(servicoAtualizado.getDuracaoMinutos());

        Servico servicoSalvo = servicoRepository.save(servicoExistente);
        return ResponseEntity.ok(servicoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado para exclusão.");
        }
        servicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}