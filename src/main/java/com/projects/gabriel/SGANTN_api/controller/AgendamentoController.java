package com.projects.gabriel.SGANTN_api.controller;

import com.projects.gabriel.SGANTN_api.model.Agendamento;
import com.projects.gabriel.SGANTN_api.model.Servico;
import com.projects.gabriel.SGANTN_api.model.Usuario;
import com.projects.gabriel.SGANTN_api.repository.AgendamentoRepository;
import com.projects.gabriel.SGANTN_api.repository.ServicoRepository;
import com.projects.gabriel.SGANTN_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody Agendamento agendamento) {

        if (agendamento.getCliente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O objeto do cliente é obrigatório.");
        }
        if (agendamento.getPrestador() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O objeto do prestador é obrigatório.");
        }
        if (agendamento.getServico() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O objeto do serviço é obrigatório.");
        }

        if (agendamento.getCliente().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID do cliente é obrigatório.");
        }
        if (agendamento.getPrestador().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID do prestador é obrigatório.");
        }
        if (agendamento.getServico().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID do serviço é obrigatório.");
        }

        Usuario cliente = usuarioRepository.findById(agendamento.getCliente().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado no banco de dados."));

        Usuario prestador = usuarioRepository.findById(agendamento.getPrestador().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado no banco de dados."));

        Servico servico = servicoRepository.findById(agendamento.getServico().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado no banco de dados."));

        agendamento.setCliente(cliente);
        agendamento.setPrestador(prestador);
        agendamento.setServico(servico);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarAgendamentos(@RequestBody Agendamento agendamento) {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        return ResponseEntity.ok(agendamentos);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado para exclusão.");
        }
        agendamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}