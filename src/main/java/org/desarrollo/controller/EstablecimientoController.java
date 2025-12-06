package org.desarrollo.controller;


import org.desarrollo.dto.EstablecimientoMesasDTO;
import org.desarrollo.dto.EstablecimientoRequestDTO;
import org.desarrollo.dto.EstablecimientoResponseDTO;
import org.desarrollo.model.*;
import org.desarrollo.service.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService servicio;

    public EstablecimientoController(EstablecimientoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<EstablecimientoResponseDTO>> listarEstablecimentos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstablecimientoResponseDTO> listarPorId(@PathVariable Integer id) {
        EstablecimientoResponseDTO dto = servicio.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<EstablecimientoResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<EstablecimientoResponseDTO> laLista = servicio.listaPornombre(nombre);
        return laLista != null ? ResponseEntity.ok(laLista) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar-establecimiento-sin-mesas")
    public ResponseEntity<List<EstablecimientoResponseDTO>> buscoEstablecimientosSinMesas() {
        return ResponseEntity.ok(servicio.listaEstablecimientosSinMesa());
    }

    @GetMapping("/buscar-establecimiento-con-mesas")
    public ResponseEntity<List<EstablecimientoResponseDTO>> buscoEstablecimientosConMesas() {
        return ResponseEntity.ok(servicio.listaEstablecimientoConMesas());
    }


    @PostMapping
    public ResponseEntity<EstablecimientoResponseDTO> guardarEstablecimiento(@RequestBody EstablecimientoRequestDTO dto) {
        return ResponseEntity.ok(servicio.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> actualizarEstablecimiento(@PathVariable Integer id, @RequestBody EstablecimientoRequestDTO dto) {
        boolean ok = servicio.actualizo(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<Boolean> desactivoEstablecimiento(@PathVariable Integer id) {
        boolean ok = servicio.desactivar(id);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
    @GetMapping("/mesas-resumen/{id}")
    public ResponseEntity<EstablecimientoMesasDTO> resumenMesa(@PathVariable Integer id) {
        EstablecimientoMesasDTO objeto = servicio.mesasResumen(id);
        return ResponseEntity.ok(objeto);
    }

}
