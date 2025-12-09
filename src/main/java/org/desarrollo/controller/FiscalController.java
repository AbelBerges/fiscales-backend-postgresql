package org.desarrollo.controller;


import org.desarrollo.dto.FiscalListaDTO;
import org.desarrollo.dto.FiscalRequestDTO;
import org.desarrollo.dto.FiscalResponseDTO;
import org.desarrollo.mapper.FiscalMapper;
import org.desarrollo.model.Fiscal;
import org.desarrollo.service.FiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/fiscales")
public class FiscalController {

    @Autowired
    private FiscalService servicio;


    @GetMapping
    public ResponseEntity<List<FiscalListaDTO>> listarFiscales() {
        return ResponseEntity.ok(servicio.listarFiscalesActivosOptimizado());
    }

    @GetMapping("/busqueda_optimizada/")
    public ResponseEntity<List<FiscalListaDTO>> busquedaOptimizada(@RequestParam(required = false) Integer idTipoFiscal,
                                                                   @RequestParam(required = false) Integer idJornada,
                                                                   @RequestParam(required = false) Boolean activo,
                                                                   @RequestParam(required = false) String apellido) {
        if (apellido == null ||apellido.isBlank()) {
            apellido = "";
        } else {
            apellido = apellido.toUpperCase(Locale.ROOT);
        }
        List<FiscalListaDTO> resultado = servicio.buscarTodosOptimizado(idTipoFiscal, idJornada, activo, apellido);
        return ResponseEntity.ok(resultado);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FiscalResponseDTO> buscarFiscalPorId(@PathVariable Integer id) {
        FiscalResponseDTO dto = servicio.buscoPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/apellidos")
    public ResponseEntity<List<String>> listarTodosPorApellido() {
        return ResponseEntity.ok(servicio.buscarTodosPorApellidos());
    }

    @GetMapping("/buscar/{apellido}")
    public ResponseEntity<List<FiscalResponseDTO>> listarPorApelllido(@PathVariable String apellido) {
        return ResponseEntity.ok(servicio.listaPorApellido(apellido));
    }

    @GetMapping("/tipo-fiscal/{id}")
    public ResponseEntity<List<FiscalResponseDTO>> listarPorTipofiscal(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.listarPorTipoFiscal(id));
    }

    @GetMapping("/fiscal-establecimiento-asignado/{id}")
    public ResponseEntity<List<FiscalResponseDTO>> listarFiscalGeneralPorEstablecimiento(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.listarPorEstablecimientoAsignado(id));
    }

    @GetMapping("/tipo-fiscal-sin-establecimiento/{id}")
    public ResponseEntity<List<FiscalResponseDTO>> listaTipoFiscalSinEstablecimiento(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.listarTipoFiscalSinEstablecimiento(id));
    }

    @GetMapping("/fiscal-tipo/{idTipo}/establecimiento-asignado/{idEst}")
    public ResponseEntity<List<FiscalResponseDTO>> listarPorTipoFiscalIdEstablecimientoAsignado(@PathVariable Integer idTipo, Integer idEst) {
        return ResponseEntity.ok(servicio.listarPorIdTipoFiscalAndIdEstablecimientoAsignado(idTipo, idEst));
    }

    @GetMapping("/sin-establecimiento-asignado")
    public ResponseEntity<List<FiscalResponseDTO>> listarSinEstablecimientoAsignado() {
        return ResponseEntity.ok(servicio.listarSinEstablecimientoAsignado());
    }

    @GetMapping("/fiscales-comunes-sin-mesa/{idTipoFiscal}")
    public ResponseEntity<List<FiscalResponseDTO>> recuperarFiscalesSinMesa(@PathVariable Integer idTipoFiscal) {
        return ResponseEntity.ok(servicio.listarFiscalesComuneSinMesa(idTipoFiscal));
    }

    @GetMapping("/fiscal-por-id-mesa/{idMesa}")
    public ResponseEntity<List<FiscalResponseDTO>> buscoFiscalPorIdMesa(@PathVariable Integer idMesa) {
        return ResponseEntity.ok(servicio.buscarFiscalPorIdMesa(idMesa));
    }

    @GetMapping("/fiscal-tipo/{idTipoFiscal}/jornada/{idJornada}/sinmesa/")
    public ResponseEntity<List<FiscalResponseDTO>> listaTipoFiscalJornadaSinMesa(@PathVariable Integer idTipoFiscal,@PathVariable Integer idJornada) {
        return ResponseEntity.ok(servicio.listaFiscalesTipoFiscalJornadaMesaNull(idTipoFiscal, idJornada));
    }

    @GetMapping("/fiscal-tipo/{idTipoFiscal}/jornada/{idJornada}/{idMesa}")
    public ResponseEntity<FiscalResponseDTO> buscoFiscalTipoFiscalJornadaMesa(@PathVariable Integer idTipoFiscal,@PathVariable Integer idJornada,@PathVariable Integer idMesa) {
        return servicio.buscarFiscalTipoFiscalJornadaMesa(idTipoFiscal, idJornada, idMesa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*@GetMapping("/opciones-buscar")
    public ResponseEntity<List<FiscalResponseDTO>> busquedasPorFiltros(@RequestParam(required = false) Integer idTipoFiscal,
                                                            @RequestParam(required = false) Integer idJornada,
                                                            @RequestParam(required = false) Boolean activo,
                                                            @RequestParam(required = false) String apellido) {
        if (apellido == null || apellido.isBlank()) {
            apellido = "";
        } else {
            apellido = apellido.toUpperCase(Locale.ROOT);
        }
        List<FiscalResponseDTO> resultado = servicio.busquedaParaFiltros(idTipoFiscal, idJornada, activo, apellido);
        return ResponseEntity.ok(resultado);
    }*/

    @PostMapping
    public ResponseEntity<FiscalResponseDTO> guardarFiscal(@RequestBody FiscalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.guardarFiscal(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarFiscal(@PathVariable Integer id, @RequestBody FiscalRequestDTO dto) {
        servicio.actualizarFiscal(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<Boolean> desactivar(@PathVariable Integer id) {
        boolean ok = servicio.desactivarFiscal(id);
        return ok ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{idFiscal}/asignar/{idEstablecimiento}")
    ResponseEntity<Void> asignarFiscalGeneral(@PathVariable Integer idFiscal, @PathVariable Integer idEstablecimiento) {
        servicio.asignarFiscalAEstablecimiento(idFiscal,idEstablecimiento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idFiscal}/asignar-fiscal-mesa/{idMesa}")
    ResponseEntity<Void> asignarFiscalAMesa(@PathVariable Integer idFiscal, @PathVariable Integer idMesa) {
        servicio.asignarFiscalAMesa(idFiscal, idMesa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/desasignar-fiscal-mesa/{idFiscal}")
    public ResponseEntity<Void> desasignarFiscalMesa(@PathVariable Integer idFiscal) {
        servicio.desasignarFiscalAUnaMesa(idFiscal);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/desasignar-fiscal-general/{idFiscal}")
    public ResponseEntity<Void> desasignarUnFiscalGeneral(@PathVariable Integer idFiscal) {
        servicio.desasignarFiscalGeneral(idFiscal);
        return ResponseEntity.ok().build();
    }

}
