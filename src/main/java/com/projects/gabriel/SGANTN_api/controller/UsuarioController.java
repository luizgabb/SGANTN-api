package com.projects.gabriel.SGANTN_api.controller;

import com.projects.gabriel.SGANTN_api.model.Usuario;
import com.projects.gabriel.SGANTN_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este e-mail já está cadastrado.");
        }
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para atualização."));

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setTipo(usuarioAtualizado.getTipo());

        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail())) {
            if (usuarioRepository.findByEmail(usuarioAtualizado.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O novo e-mail já está em uso.");
            }
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}