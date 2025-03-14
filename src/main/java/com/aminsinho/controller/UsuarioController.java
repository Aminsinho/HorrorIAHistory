package com.aminsinho.controller;

import com.aminsinho.models.Usuario;
import com.aminsinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("usuario/{id}")
    public Usuario getUsuario(@PathVariable("id") Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
