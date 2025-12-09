package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.desarrollo.dto.FiscalListaDTO;
import org.desarrollo.dto.FiscalRequestDTO;
import org.desarrollo.dto.FiscalResponseDTO;
import org.desarrollo.mapper.FiscalMapper;
import org.desarrollo.model.Establecimiento;
import org.desarrollo.model.Fiscal;
import org.desarrollo.model.Mesa;
import org.desarrollo.repository.EstablecimientoRepository;
import org.desarrollo.repository.FiscalRepository;
import org.desarrollo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FiscalService {

    @Autowired
    private FiscalRepository repo;
    @Autowired
    private EntityManager em;
    @Autowired
    private MesaRepository repoMesa;
    @Autowired
    private EstablecimientoRepository estRepo;
    @Autowired
    private MesaRepository mesaRepo;


    public List<FiscalListaDTO> listarFiscalesActivosOptimizado() {
        return repo.todosOptimizado();
    }

    public List<FiscalResponseDTO> listarPorTipoFiscal(Integer id) {
        return repo.findByTipoFiscal_IdTipoFiscal(id)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarPorEstablecimientoAsignado(Integer id) {
        return repo.findByEstablecimientoAsignado_IdEstablecimiento(id)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarSinEstablecimientoAsignado() {
        return repo.findByEstablecimientoAsignado_IdEstablecimientoIsNull()
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarTipoFiscalSinEstablecimiento(Integer idTipoFiscal) {
        return repo.findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoIsNull(idTipoFiscal)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarPorIdTipoFiscalAndIdEstablecimientoAsignado(Integer idtf, Integer idEstAsig) {
        return repo.findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimiento(idtf, idEstAsig)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public FiscalResponseDTO buscoPorId(Integer id) {
        return repo.findById(id)
                .map(FiscalMapper::aEntidadResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el fiscal"));
    }

    public List<FiscalResponseDTO> listaPorApellido(String apellido) {
        List<FiscalResponseDTO> laLista = repo.findByApellidoFiscalContainingIgnoreCase(apellido).stream()
                .map(FiscalMapper::aEntidadResponseDTO).toList();
        return laLista;
    }

    public List<FiscalResponseDTO> listarFiscalesComuneSinMesa(Integer idTipoFiscal) {
        return repo.findByTipoFiscal_IdTipoFiscalAndMesa_IdMesaIsNull(idTipoFiscal)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> buscarFiscalPorIdMesa(Integer idMesa) {
        return repo.findByMesa_IdMesa(idMesa)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listaFiscalesTipoFiscalJornadaMesaNull(Integer idTipoFiscal, Integer idJornada) {
        return repo.findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaIsNull(idTipoFiscal, idJornada)
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public Optional<FiscalResponseDTO> buscarFiscalTipoFiscalJornadaMesa(Integer idTipoFiscal, Integer idJornada, Integer idMesa) {
        return repo.findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesa(idTipoFiscal, idJornada, idMesa)
                .map(FiscalMapper::aEntidadResponseDTO);
    }

    /*public List<FiscalResponseDTO> busquedaParaFiltros(Integer idTipoFiscal, Integer idJornada, Boolean activo, String apellido) {
        return repo.buscarConFiltros(idTipoFiscal, idJornada, activo, apellido).stream()
                .map(FiscalMapper::aEntidadResponseDTO).toList();
    }*/

    public List<FiscalListaDTO> buscarTodosOptimizado(Integer idTipoFiscal, Integer idJornada, Boolean activo, String apellido) {
        return repo.buscarOptimizado(idTipoFiscal, idJornada, activo, apellido).stream().toList();
    }

    public List<String> buscarTodosPorApellidos() {
        return repo.apellidos();
    }

    @Transactional
    public FiscalResponseDTO guardarFiscal(FiscalRequestDTO fiscalDto) {
        // validamos que si viene una mesa asignada que exista y esté disponible.
        if (fiscalDto.idMesa() != null) {
            if (repo.existsByMesa_IdMesa(fiscalDto.idMesa())) {
                throw new IllegalStateException("La mesa tiene un fiscal asignado");
            }
            if (!repoMesa.existsById(fiscalDto.idMesa())) {
                throw new EntityNotFoundException("Mesa no encontrada");
            }
        }
        Fiscal convierto = FiscalMapper.aEntidadDeCreacion(fiscalDto, em);
        Fiscal nuevo = repo.save(convierto);
        return FiscalMapper.aEntidadResponseDTO(nuevo);
    }

    @Transactional
    public void actualizarFiscal(Integer id, FiscalRequestDTO dto) {
        System.out.println("quiero ver el dto " + dto);
        Fiscal existe = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el fical"));

        //Si se quiere asignar una nueva mesa, validar disponibilidad
        if (dto.idMesa() != null) {
            boolean ocupada = repo.existsByMesa_IdMesa(dto.idMesa());
            boolean laMisma = existe.getMesa() != null &&Objects.equals(existe.getMesa().getIdMesa(), dto.idMesa());
            if (ocupada && !laMisma) {
                throw new IllegalStateException("La mesa ya está ocupada por otro fiscal ");
            }
        }
        FiscalMapper.paraActualizarFiscal(dto, existe, em);
        repo.save(existe);
    }

    @Transactional
    public boolean desactivarFiscal(Integer idFiscal) {
        return repo.findById(idFiscal).map(existe -> {
            existe.setActivo(false);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    @Transactional
    public void asignarFiscalAEstablecimiento(Integer idFiscal, Integer idEstablecimiento) {
        Fiscal fiscal = repo.findById(idFiscal)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el fiscal"));
        Establecimiento est = estRepo.findById(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el establecimiento"));
        fiscal.setEstablecimientoAsignado(est);
        repo.save(fiscal);
    }

    @Transactional
    public void asignarFiscalAMesa(Integer idFiscal, Integer idMesa) {
        Mesa mesa = mesaRepo.findById(idMesa).orElseThrow(() -> new RuntimeException("No se ha encontrado la mesa"));
        Fiscal fiscal = repo.findById(idFiscal).orElseThrow(() -> new RuntimeException("No se ha encontrado el fiscal"));
        if (fiscal.getMesa() != null) {
            throw new RuntimeException("El fiscal ya fue asignado a una mesas");
        }
        fiscal.setMesa(mesa);
        repo.save(fiscal);
    }

    @Transactional
    public void desasignarFiscalAUnaMesa(Integer idFiscal) {
        Fiscal fiscal = repo.findById(idFiscal).orElseThrow(() -> new RuntimeException("No se ha encontrado el fiscal"));
        fiscal.setMesa(null);
        repo.save(fiscal);
    }

    @Transactional
    public void desasignarFiscalGeneral(Integer idFiscal) {
        Fiscal fiscal = repo.findById(idFiscal).orElseThrow(() ->  new RuntimeException("No se ha encontrado el fiscal general"));
        fiscal.setEstablecimientoAsignado(null);
        repo.save(fiscal);
    }
}
