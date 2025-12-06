package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.desarrollo.dto.AsignacionMesasRequestDTO;
import org.desarrollo.dto.MesaRequestDTO;
import org.desarrollo.dto.MesaResponseDTO;
import org.desarrollo.mapper.MesaMapper;
import org.desarrollo.model.Establecimiento;
import org.desarrollo.model.Mesa;
import org.desarrollo.repository.EstablecimientoRepository;
import org.desarrollo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository repoMesa;
    @Autowired
    private EstablecimientoRepository repoEst;
    @Autowired
    private EntityManager em;

    public List<MesaResponseDTO> listarMesas() {
        return repoMesa.findAll()
                .stream()
                .map(MesaMapper::aResponseDTO)
                .toList();
    }

    public MesaResponseDTO buscarPorId(Integer id) {
        return repoMesa.findById(id)
                .map(MesaMapper::aResponseDTO)
                .orElse(null);
    }

    public MesaResponseDTO buscarPorNumeroMesa(Integer numeroMesa) {
        Optional<Mesa> encontrada = repoMesa.findByNumeroMesa(numeroMesa);
        if (encontrada.isEmpty()) {
            throw new EntityNotFoundException(("No se encontró la mesa "));
        }
        return MesaMapper.aResponseDTO(encontrada.get());

    }

    @Transactional
    public MesaResponseDTO guardarMesa(MesaRequestDTO dto) {
        Mesa crear = MesaMapper.aEntidadCreacion(dto, em);
        if (repoMesa.existsByNumeroMesaAndEstablecimiento_IdEstablecimiento(crear.getNumeroMesa(), crear.getIdMesa())) {
            throw new IllegalArgumentException("Ya existe una mesa con ese número en este establecimiento");
        }
        Mesa guardado = repoMesa.save(crear);
        return MesaMapper.aResponseDTO(guardado);
    }

    public List<MesaResponseDTO> listarSinEstablecimientos() {
        return repoMesa.findByEstablecimiento_IdEstablecimientoIsNull()
                .stream()
                .map(MesaMapper::aResponseDTO)
                .toList();
    }

    @Transactional
    public void actualizoMesa(Integer id, MesaRequestDTO dto) {
        Mesa existe = repoMesa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la mesa "));
        MesaMapper.paraActualziarMesa(dto, existe, em);
        repoMesa.save(existe);
        /*return repoMesa.findById(id).map(m -> {
            MesaMapper.paraActualziarMesa(dto, m, em);
            repoMesa.save(m);
            return true;
        }).orElse(false);
         */
    }
    @Transactional
    public void asignarMesas(Integer idEstablecimiento, List<Integer> numerosMesas) {
        Establecimiento est = repoEst.findById(idEstablecimiento)
                .orElseThrow(() -> new IllegalArgumentException("Establecimineto no encontrado"));
        List<Mesa> mesas = repoMesa.findByNumeroMesaIn(numerosMesas);
        mesas.forEach(m -> m.setEstablecimiento(est));
        repoMesa.saveAll(mesas);
    }

    @Transactional
    public void actualizarMesasAsignadas(Integer idEstablecimiento, List<Integer> nuevasMesas) {
        Establecimiento establecimiento = repoEst.findById(idEstablecimiento)
                .orElseThrow(() -> new IllegalArgumentException("Establecimiento no encontrado"));
        // 1) Armamos la lista con las mesas asignadas actualmente
        List<Mesa> actuales = repoMesa.findByEstablecimiento_IdEstablecimiento(idEstablecimiento);
        // 2) Las liberamos de su asiganación
        actuales.forEach(m-> m.setEstablecimiento(null));
        repoMesa.saveAll(actuales);
        // 3) Asignamos las nuevas mesas
        if (!nuevasMesas.isEmpty()) {
            List<Mesa> nuevas = repoMesa.findByNumeroMesaIn(nuevasMesas);
            nuevas.forEach(m -> m.setEstablecimiento(establecimiento));
            repoMesa.saveAll(nuevas);
        }
    }

    public AsignacionMesasRequestDTO mesasPorEstablecimiento(Integer id) {
        List<Mesa> lista = repoMesa.findByEstablecimiento_IdEstablecimiento(id).stream().toList();
        List<Integer> mesas = new ArrayList<>();
       for (int i = 0;i < lista.size();i++) {
           mesas.add(lista.get(i).getNumeroMesa());
       }
        return new AsignacionMesasRequestDTO(id, mesas);

    }
}
