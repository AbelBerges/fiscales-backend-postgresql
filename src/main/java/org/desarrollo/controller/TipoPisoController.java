package org.desarrollo.controller;

import org.desarrollo.dto.TipoPisoRequestDTO;
import org.desarrollo.dto.TipoPisoResponseDTO;
import org.desarrollo.repository.TipoPisoRepository;
import org.desarrollo.service.TipoPisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tipospisos")
public class TipoPisoController {

    //@Autowired
    //private TipoPisoRepository repo;

    private TipoPisoService service;

    public TipoPisoController(TipoPisoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoPisoResponseDTO>> listarTipoPiso() {
        return ResponseEntity.ok(service.listarTiposPisos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPisoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.porIdTipoTipo(id));
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<TipoPisoResponseDTO>> listarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.listarPorNombre(nombre));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TipoPisoResponseDTO> guardarTipoPiso(@RequestBody TipoPisoRequestDTO dto) {
        return ResponseEntity.ok(service.guardarTP(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Boolean> acutalizarTipoPiso(@PathVariable Integer id, @RequestBody TipoPisoRequestDTO dto) {
        boolean ok = service.actualizarTipoPiso(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
}
