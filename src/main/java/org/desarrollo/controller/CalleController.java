package org.desarrollo.controller;

import org.desarrollo.dto.CalleRequestDTO;
import org.desarrollo.dto.CalleResponseDTO;
import org.desarrollo.repository.CalleRepository;
import org.desarrollo.service.CalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/calles")
public class CalleController {

    @Autowired
    private CalleRepository repo;
    @Autowired
    private CalleService servicio;

    public CalleController(CalleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<CalleResponseDTO>> listarCalles() {
        return ResponseEntity.ok(servicio.listarTodasLasCalles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalleResponseDTO> buscaCalle(@PathVariable int id) {
        CalleResponseDTO dto = servicio.buscoPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<CalleResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicio.listarPorNombreCalle(nombre));
    }

    @PostMapping
    public ResponseEntity<CalleResponseDTO> guardarCalle(@RequestBody CalleRequestDTO dto) {
        return ResponseEntity.ok(servicio.guardoCalle(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> actualizarCalle(@PathVariable int id, @RequestBody CalleRequestDTO dto) {
        boolean ok = servicio.actualizoCalle(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

}
