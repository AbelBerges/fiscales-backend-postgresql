package org.desarrollo.controller;

import org.desarrollo.model.Fiscal;
import org.desarrollo.model.FotoFiscal;
import org.desarrollo.repository.FiscalRepository;
import org.desarrollo.repository.FotoFiscalRepository;
import org.desarrollo.service.FotoFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fiscales/{idFiscal}/foto")
public class FotoFiscalController {

    @Autowired
    private FiscalRepository fiscalRepo;

    @Autowired
    private FotoFiscalRepository fotoRepo;
    @Autowired
    private FotoFiscalService servicio;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<Boolean> subirFoto(@PathVariable Integer idFiscal, @RequestParam("file")MultipartFile archivo) throws IOException {
        boolean ok = servicio.guardarFoto(idFiscal, archivo);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Integer idFiscal) {
        FotoFiscal foto = fotoRepo.findByFiscalIdFiscal(idFiscal).orElseThrow(() -> new RuntimeException("Foto no encontrada"));
        return ResponseEntity.ok(foto.getImagen());
    }
}
