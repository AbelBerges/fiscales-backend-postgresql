package org.desarrollo.controller;


import org.desarrollo.dto.DireccionRequestDTO;
import org.desarrollo.dto.DireccionResponseDTO;
import org.desarrollo.repository.DireccionRepository;
import org.desarrollo.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/direcciones")
public class DireccionController {

    @Autowired
    private DireccionRepository repo;
    @Autowired
    private DireccionService servicio;

    public DireccionController(DireccionService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<DireccionResponseDTO>> listarDirecciones() {
        return ResponseEntity.ok(servicio.listarDirecciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionResponseDTO> buscarDireccion(@PathVariable int id) {
        DireccionResponseDTO dto = servicio.buscoDirPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/porCalle/{idCalle}")
    public ResponseEntity<List<DireccionResponseDTO>> listarPorCalle(@PathVariable Integer idCalle) {
        return ResponseEntity.ok(servicio.listaPorCalle(idCalle));
    }

    @PostMapping
    public ResponseEntity<DireccionResponseDTO> guardarDirecion(@RequestBody DireccionRequestDTO dto) {
        return ResponseEntity.ok(servicio.guardarDireccion(dto));
    }

    @PutMapping("/{idCalle}")
    public ResponseEntity<Boolean> acutualizarDireccion(@PathVariable Integer idCalle, @RequestBody DireccionRequestDTO dto) {
        boolean ok = servicio.actualizoDireccion(idCalle, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }



}
