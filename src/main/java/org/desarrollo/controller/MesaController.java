package org.desarrollo.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.desarrollo.dto.AsignacionMesasRequestDTO;
import org.desarrollo.dto.MesaRequestDTO;
import org.desarrollo.dto.MesaResponseDTO;
import org.desarrollo.model.ErrorResponse;
import org.desarrollo.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService servicio;

    @GetMapping
    public ResponseEntity<List<MesaResponseDTO>> listarMesas() {
        return ResponseEntity.ok(servicio.listarMesas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDTO> buscarMesaPorId(@PathVariable Integer id) {
        MesaResponseDTO mesa = servicio.buscarPorId(id);
        return mesa != null ? ResponseEntity.ok(mesa) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar/{mesa}")
    public ResponseEntity<?> buscoPorNumeroMesa(@PathVariable Integer mesa) {
        MesaResponseDTO mesaDto = servicio.buscarPorNumeroMesa(mesa);
        return ResponseEntity.ok(mesaDto);
    }

    @PostMapping
    public ResponseEntity<MesaResponseDTO> guardarMesa(@RequestBody MesaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.guardarMesa(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarMesa(@PathVariable Integer id, @RequestBody MesaRequestDTO dto) {
        servicio.actualizoMesa(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sin-establecimiento")
    public ResponseEntity<List<MesaResponseDTO>> listaMesaSinEstablecimiento() {
        return ResponseEntity.ok(servicio.listarSinEstablecimientos());
    }

    @PutMapping("/asignar")
    public ResponseEntity<?> asignarMesas(@RequestBody AsignacionMesasRequestDTO requestDTO) {
        try {
            servicio.asignarMesas(requestDTO.idEstablecimiento(), requestDTO.numerosMesa());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asignar las mesas" + e.getMessage());
        }
    }

    @PutMapping("/actualizar-mesas")
    public ResponseEntity<?> actualizoMesas(@RequestBody AsignacionMesasRequestDTO requestDTO) {
        try {
            servicio.actualizarMesasAsignadas(requestDTO.idEstablecimiento(), requestDTO.numerosMesa());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar las mesas" + e.getMessage());
        }
    }

    @GetMapping("/mesas-establecimiento/{id}")
    public ResponseEntity<AsignacionMesasRequestDTO> mesasPorEstablecimiento(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.mesasPorEstablecimiento(id));
    }
}
