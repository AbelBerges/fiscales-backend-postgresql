package org.desarrollo.controller;

import org.desarrollo.dto.TipoFiscalRequestDTO;
import org.desarrollo.dto.TipoFiscalResponseDTO;
import org.desarrollo.mapper.TipoFiscalMapper;
import org.desarrollo.repository.TipoFiscalRepository;
import org.desarrollo.service.TipoFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tiposfiscales")
public class TipoFiscalController {

    @Autowired

    private TipoFiscalService servicio;

    public TipoFiscalController( TipoFiscalService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<TipoFiscalResponseDTO>> listarTiposFiscales() {
        return ResponseEntity.ok(servicio.listarTiposFiscal());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoFiscalResponseDTO> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(servicio.buscarPorId(id));
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<TipoFiscalResponseDTO> listarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicio.listaPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<TipoFiscalResponseDTO> guardarNuevoTP(@RequestBody TipoFiscalRequestDTO dto) {
        return ResponseEntity.ok(servicio.guardarTipoFiscal(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> actualizarTipoFiscal(@PathVariable int id, @RequestBody TipoFiscalRequestDTO dto) {
        boolean ok = servicio.actualizarTipoFiscal(id, dto);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }


}
