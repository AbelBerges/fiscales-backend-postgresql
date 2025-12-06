package org.desarrollo.controller;

import org.desarrollo.dto.TipoDeparamentoRequestDTO;
import org.desarrollo.dto.TipoDepartamentoResponseDTO;
import org.desarrollo.mapper.TipoDepartamentoMapper;
import org.desarrollo.model.TipoDepartamento;
import org.desarrollo.repository.TipoDepartamentoRepository;
import org.desarrollo.service.TipoDepartamentoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tiposdepartamentos")
public class TipoDepartamentoController {

    @Autowired
    private TipoDepartamentoRepository repo;
    @Autowired
    private TipoDepartamentoservice servicio;

    public TipoDepartamentoController(TipoDepartamentoRepository repo) {
        this.repo = repo;
    }


    @GetMapping
    public ResponseEntity<List<TipoDepartamentoResponseDTO>> listarTiposDepartamentos() {
        return ResponseEntity.ok(servicio.listarTipos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TipoDepartamentoResponseDTO> listarPorId(@PathVariable int id) {
        return ResponseEntity.ok(servicio.buscoPorId(id));
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<TipoDepartamentoResponseDTO>> listarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicio.listaPorNombre(nombre));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TipoDepartamentoResponseDTO> guardarTipoDepartamento(@RequestBody TipoDeparamentoRequestDTO dto) {
        return ResponseEntity.ok(servicio.guardarTD(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Boolean> actualizarTipoDepartamento(@PathVariable int id, @RequestBody TipoDeparamentoRequestDTO dto) {
        boolean ok = servicio.actualizarTd(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/desactivar/{id}")
    @Transactional
    public ResponseEntity<Boolean> descativarTipoDpto(@PathVariable Integer id) {
        boolean ok = servicio.desactivarTd(id);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
}
