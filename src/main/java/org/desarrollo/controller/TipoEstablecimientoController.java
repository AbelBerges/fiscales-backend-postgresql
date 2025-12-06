package org.desarrollo.controller;

import org.desarrollo.dto.TipoEstablecimientoRequestDTO;
import org.desarrollo.dto.TipoEstablecimientoResponseDTO;
import org.desarrollo.mapper.TipoEstablecimientoMapper;
import org.desarrollo.model.TipoEstablecimiento;
import org.desarrollo.repository.TipoEstablecimientoRepository;
import org.desarrollo.service.TipoEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tipoestablecimiento")
public class TipoEstablecimientoController {

    @Autowired
    private TipoEstablecimientoRepository repo;
    @Autowired
    private TipoEstablecimientoService service;

    public TipoEstablecimientoController(TipoEstablecimientoRepository repo) {
        this.repo = repo;
    }


    @GetMapping
    public ResponseEntity<List<TipoEstablecimientoResponseDTO>> listarTiposEstablecimientos() {
        List<TipoEstablecimientoResponseDTO> lista = service.listarTipoEstablecimiento();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEstablecimientoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscoPorIdTipoEst(id));
    }

    @GetMapping("/buscar/{tipo}")
    public ResponseEntity<List<TipoEstablecimientoResponseDTO>> buscarPorNombre(@PathVariable String tipo) {
        List<TipoEstablecimientoResponseDTO> lista = service.buscarPorElTipo(tipo);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TipoEstablecimientoResponseDTO> guardarTipoEstablecimiento(@RequestBody TipoEstablecimientoRequestDTO dto) {
        return ResponseEntity.ok(service.guardarTipoEst(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Boolean> actualizarTipoEstablecimiento(@PathVariable Integer id, @RequestBody TipoEstablecimientoRequestDTO dto) {
        boolean ok = service.actualizarTipoEst(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/desactivar/{id}")
    @Transactional
    public ResponseEntity<Boolean> desactivarTipoEst(@PathVariable Integer id) {
        boolean ok = service.desactivarTipoEst(id);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
}
