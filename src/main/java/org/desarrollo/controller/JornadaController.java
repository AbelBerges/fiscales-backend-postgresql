package org.desarrollo.controller;

import org.desarrollo.dto.JornadaRequestDTO;
import org.desarrollo.dto.JornadaResponseDTO;
import org.desarrollo.service.JornadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/jornadas")
public class JornadaController {
    private JornadaService servicio;

    public JornadaController(JornadaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<JornadaResponseDTO>> listarJornadas() {
        return ResponseEntity.ok(servicio.listarJornadas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JornadaResponseDTO> buscoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.buscarPorId(id));
    }

    @GetMapping("/buscar/{tipojornada}")
    public ResponseEntity<JornadaResponseDTO> listarPorTipo(@PathVariable String tipojornada) {
        return ResponseEntity.ok(servicio.listarPorTipo(tipojornada));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<JornadaResponseDTO> guardoJornada(@RequestBody JornadaRequestDTO dto) {
        return ResponseEntity.ok(servicio.guardarJornada(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Boolean> actualizoJornada(@PathVariable Integer id, @RequestBody JornadaRequestDTO dto) {
        boolean ok = servicio.actualizarJornada(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
}
