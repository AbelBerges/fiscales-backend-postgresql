package org.desarrollo.controller;

import org.desarrollo.dto.*;
import org.desarrollo.mapper.UsuarioMapper;
import org.desarrollo.model.Usuario;
import org.desarrollo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Configuration
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;
    private PasswordEncoder codificador;

    public UsuarioController(UsuarioRepository repo, PasswordEncoder codificador) {
        this.repo = repo;
        this.codificador = codificador;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = repo.findAll().stream()
                .map(UsuarioMapper::aEntidadUsuarioResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable int id) {
        return repo.findById(id)
                .map(UsuarioMapper::aEntidadUsuarioResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/buscar/{apellido}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorApellido(@PathVariable String apellido) {
        List<UsuarioResponseDTO> listar = repo.findByApellidoUsuarioContainingIgnoreCase(apellido).stream()
                .map(UsuarioMapper::aEntidadUsuarioResponse)
                .toList();
        return ResponseEntity.ok(listar);
    }

    @GetMapping("/buscarPorUser/{nomUser}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorNomUser(@PathVariable String nomUser) {
        List<UsuarioResponseDTO> listarUser = repo.findByNomUserContainingIgnoreCase(nomUser)
                .stream()
                .map(UsuarioMapper::aEntidadUsuarioResponse)
                .toList();
        return ResponseEntity.ok(listarUser);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> guardarUsuario(@RequestBody UsuarioResquestDTO dto) {
        Usuario nuevo = UsuarioMapper.aEntidadDeCreacion(dto);
        String hash = codificador.encode(nuevo.getClave());
        nuevo.setClave(hash);
        Usuario guardado = repo.save(nuevo);
        return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable int id, @RequestBody UsuarioActualizarDTO dto) {
        return repo.findById(id).map(existe -> {
            UsuarioMapper.aplicarActualizarUsuario(dto, existe);
            Usuario actualizado = repo.save(existe);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/clave/{id}")
    public ResponseEntity<UsuarioResponseDTO> acutalizarClaveUsuario(@PathVariable int id, @RequestBody UsuarioActualizarClaveDTO claveDto) {
        return repo.findById(id).map(existe -> {
            String hash = codificador.encode(claveDto.clave());
            existe.setClave(hash);
            Usuario actualizar = repo.save(existe);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizar));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/username/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarNomUserUsuario(@PathVariable int id, @RequestBody UsuarioActualizarNomUserDTO nomUserDto) {
        return repo.findById(id).map(existe -> {
            existe.setNomUser(nomUserDto.nomuser());
            Usuario actualizo = repo.save(existe);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/baja/{id}")
    public ResponseEntity<Boolean> bajaUsuario(@PathVariable int id) {
        return repo.findById(id).map(existe -> {
            existe.setActivo(false);
            repo.save(existe);
            return ResponseEntity.ok(true);
        }).orElse(ResponseEntity.notFound().build());
    }

}
