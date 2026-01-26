package org.desarrollo.controller;

import org.desarrollo.model.Basica;
import org.desarrollo.service.BasicaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/basicas")
public class BasicaController {
    @Autowired
    private BasicaServicio servicio;

    @GetMapping("/{id}")
    public Basica recuperarBasicaPorId(@PathVariable Integer id) {
        return servicio.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Basica>> listadoBasicas() {
        try {
            return ResponseEntity.ok(servicio.todasLasBasicas());
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
