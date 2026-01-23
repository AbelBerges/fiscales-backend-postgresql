package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.desarrollo.config.ContextoBasica;
import org.desarrollo.config.ContextoUsuario;
import org.desarrollo.dto.FiscalListaDTO;
import org.desarrollo.dto.FiscalRequestDTO;
import org.desarrollo.dto.FiscalResponseDTO;
import org.desarrollo.mapper.FiscalMapper;
import org.desarrollo.model.Basica;
import org.desarrollo.model.Establecimiento;
import org.desarrollo.model.Fiscal;
import org.desarrollo.model.Mesa;
import org.desarrollo.repository.EstablecimientoRepository;
import org.desarrollo.repository.FiscalRepository;
import org.desarrollo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.todosOptimizado();
        }
        return repo.todosOptimizado(ContextoBasica.get());
    }

    public List<FiscalResponseDTO> listarPorTipoFiscal(Integer id) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByTipoFiscal_IdTipoFiscal(id).stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByTipoFiscal_IdTipoFiscalAndBasica_IdBasica(id, ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarPorEstablecimientoAsignado(Integer id) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByEstablecimientoAsignado_IdEstablecimiento(id)
                    .stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByEstablecimientoAsignado_IdEstablecimientoAndBasica_IdBasica(id, ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarSinEstablecimientoAsignado() {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByEstablecimientoAsignado_IdEstablecimientoIsNull()
                    .stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByEstablecimientoAsignado_IdEstablecimientoIsNullAndBasica_IdBasica(ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarTipoFiscalSinEstablecimiento(Integer idTipoFiscal) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoIsNull(idTipoFiscal)
                    .stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoIsNullAndBasica_IdBasica(idTipoFiscal, ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listarPorIdTipoFiscalAndIdEstablecimientoAsignado(Integer idtf, Integer idEstAsig) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimiento(idtf, idEstAsig)
                    .stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoAndBasica_IdBasica(idtf, idEstAsig, ContextoBasica.get())
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
        List<FiscalResponseDTO> laLista = new ArrayList<>();
        if (ContextoUsuario.esAdminGlobal()) {
            laLista = repo.findByApellidoFiscalContainingIgnoreCase(apellido).stream().map(FiscalMapper::aEntidadResponseDTO).toList();
        } else {
            laLista = repo.findByApellidoFiscalContainingIgnoreCaseAndBasica_IdBasica(apellido, ContextoBasica.get()).stream()
                    .map(FiscalMapper::aEntidadResponseDTO).toList();
        }
        return laLista;
    }

    public List<FiscalResponseDTO> listarFiscalesComuneSinMesa(Integer idTipoFiscal) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByTipoFiscal_IdTipoFiscalAndMesa_IdMesaIsNull(idTipoFiscal)
                    .stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByTipoFiscal_IdTipoFiscalAndMesa_IdMesaIsNullAndBasica_IdBasica(idTipoFiscal, ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> buscarFiscalPorIdMesa(Integer idMesa) {
        //List<FiscalResponseDTO> lista = new ArrayList<>();
        if (ContextoUsuario.esAdminGlobal()) {
          return repo.findByMesa_IdMesa(idMesa).stream()
                  .map(FiscalMapper::aEntidadResponseDTO)
                  .toList();
        }
        return repo.findByMesa_IdMesaAndBasica_IdBasica(idMesa, ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public List<FiscalResponseDTO> listaFiscalesTipoFiscalJornadaMesaNull(Integer idTipoFiscal, Integer idJornada) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaIsNull(idTipoFiscal, idJornada)
                    .stream()
                    .map(FiscalMapper::aEntidadResponseDTO)
                    .toList();
        }
        return repo.findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaIsNullAndBasica_IdBasica(idTipoFiscal, idJornada, ContextoBasica.get())
                .stream()
                .map(FiscalMapper::aEntidadResponseDTO)
                .toList();
    }

    public Optional<FiscalResponseDTO> buscarFiscalTipoFiscalJornadaMesa(Integer idTipoFiscal, Integer idJornada, Integer idMesa) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesa(idTipoFiscal, idJornada,idMesa)
                    .map(FiscalMapper::aEntidadResponseDTO);
        }
        return repo.findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaAndBasica_IdBasica(idTipoFiscal, idJornada, idMesa, ContextoBasica.get())
                .map(FiscalMapper::aEntidadResponseDTO);
    }

    public List<FiscalListaDTO> recuperarFiscalesPorEstablecimiento(Integer idEst) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.listarFiscalesPorEstablecimiento(idEst);
        }
        return repo.listarFiscalesPorEstablecimiento(idEst, ContextoBasica.get());
    }

    public List<FiscalListaDTO> buscarTodosOptimizado(Integer idTipoFiscal, Integer idJornada, Boolean activo, String apellido) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.buscarOptimizado(idTipoFiscal, idJornada, activo, apellido).stream().toList();
        }
        return repo.buscarOptimizado(idTipoFiscal, idJornada, activo, apellido, ContextoBasica.get()).stream().toList();
    }

    public List<String> buscarTodosPorApellidos() {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.apellidos();
        }
        return repo.apellidos(ContextoBasica.get());
    }

    @Transactional
    public FiscalResponseDTO guardarFiscal(FiscalRequestDTO fiscalDto) {
        Integer idBasica;
        if (ContextoUsuario.esAdminGlobal()) {
            throw new IllegalStateException("ADMIN_GLOBAL accede por un endpoint exclusivo");
        }
        idBasica = ContextoUsuario.getBasica();
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
        convierto.setBasica(em.getReference(Basica.class, idBasica));
        Fiscal nuevo = repo.save(convierto);
        return FiscalMapper.aEntidadResponseDTO(nuevo);
    }

    @Transactional
    public FiscalResponseDTO guardarFiscalComoAdmin(FiscalRequestDTO dto, Integer idBasica) {
        if (!ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("No tiene permisos para guardar el fiscal");
        }
        if (dto.idMesa() != null) {
            if (repo.existsByMesa_IdMesa(dto.idMesa())) {
                throw new IllegalStateException("La mesa tiene un fiscal asignado");
            }
            if (!repoMesa.existsById(dto.idMesa())) {
                throw new EntityNotFoundException("Mesa no encontrada");
            }
        }
        Fiscal convierto = FiscalMapper.aEntidadDeCreacion(dto, em);
        convierto.setBasica(em.getReference(Basica.class, idBasica));
        Fiscal guardo = repo.save(convierto);
        return FiscalMapper.aEntidadResponseDTO(guardo);
    }

    @Transactional
    public void actualizarFiscal(Integer id, FiscalRequestDTO dto) {
        Fiscal existe = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el fical"));
        if (!ContextoUsuario.esAdminGlobal()) {
            if (!Objects.equals(ContextoUsuario.getBasica(), existe.getBasica().getIdBasica())) {
                throw new SecurityException("No puede modificar fiscales de otras básicas");
            }
        }
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

    public void cambiarBasica(Integer idFiscal, Integer idBasica) {
        if (!ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("No tiene permisos para hacer está acción");
        }
        Fiscal existe = repo.findById(idFiscal).orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el fiscal"));
        if (existe.getEstablecimientoAsignado() != null) {
            throw new SecurityException("No se puede cambiar el fiscal - Tiene establecimiento asignado");
        }
        if (existe.getMesa() != null) {
            throw new SecurityException("No se puede cambiar el fiscal- Tiene mesa asignada");
        }
        existe.setBasica(em.getReference(Basica.class, idBasica));
        repo.save(existe);
    }

    @Transactional
    public boolean desactivarFiscal(Integer idFiscal) {
        return repo.findById(idFiscal).map(existe -> {
            if (existe.getMesa() != null) {
                throw new SecurityException("No se puede desactivar el fiscal - Tiene mesa asignada");
            }
            if (existe.getEstablecimientoAsignado() != null) {
                throw new SecurityException("No se puede desactivar el fiscal - Tiene establecimiento asignado");
            }
            if (!ContextoUsuario.esAdminGlobal()) {
                if (!Objects.equals(ContextoUsuario.getBasica(), existe.getBasica().getIdBasica())) {
                    throw new SecurityException("No puede desactivar un fiscal de otra básica");
                }
            }
            existe.setActivo(false);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    @Transactional
    public void asignarFiscalAEstablecimiento(Integer idFiscal, Integer idEstablecimiento) {
        if (ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("Un admin no puede hacer está tarea");
        }
        Fiscal fiscal = repo.findById(idFiscal)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el fiscal"));
        Establecimiento est = estRepo.findById(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el establecimiento"));
        Integer basica = ContextoUsuario.getBasica();
        if (!Objects.equals(fiscal.getBasica().getIdBasica(), basica) || !Objects.equals(est.getBasica().getIdBasica(), basica)) {
            throw new SecurityException("No puede asignar fiscales por fuera de su básica");
        }
        fiscal.setEstablecimientoAsignado(est);
        repo.save(fiscal);
    }

    @Transactional
    public void asignarFiscalAMesa(Integer idFiscal, Integer idMesa) {
        if (ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("Un admin no puede hacer está acción");
        }
        Mesa mesa = mesaRepo.findById(idMesa).orElseThrow(() -> new RuntimeException("No se ha encontrado la mesa"));
        Fiscal fiscal = repo.findById(idFiscal).orElseThrow(() -> new RuntimeException("No se ha encontrado el fiscal"));
        if (fiscal.getMesa() != null) {
            throw new RuntimeException("El fiscal ya fue asignado a una mesas");
        }
        Integer basica = ContextoUsuario.getBasica();
        if (!Objects.equals(mesa.getBasica().getIdBasica(), basica) || !Objects.equals(fiscal.getBasica().getIdBasica(), basica)) {
            throw new SecurityException("No puede asignar fiscal a una mesa de otras básicas");
        }
        fiscal.setMesa(mesa);
        repo.save(fiscal);
    }

    @Transactional
    public void desasignarFiscalAUnaMesa(Integer idFiscal) {
        if (ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("Un admin no puede desasignar un fiscal");
        }
        Fiscal fiscal = repo.findById(idFiscal).orElseThrow(() -> new RuntimeException("No se ha encontrado el fiscal"));
        Integer basica = ContextoUsuario.getBasica();
        if (!Objects.equals(fiscal.getBasica().getIdBasica(), basica)) {
            throw new SecurityException("No puede desasignar un fiscal de otra básica");
        }
        fiscal.setMesa(null);
        repo.save(fiscal);
    }

    @Transactional
    public void desasignarFiscalGeneral(Integer idFiscal) {
        if (ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("Un admin no puede desasignar un fiscal general");
        }
        Fiscal fiscal = repo.findById(idFiscal).orElseThrow(() ->  new RuntimeException("No se ha encontrado el fiscal general"));
        Integer basica = ContextoUsuario.getBasica();
        if (!Objects.equals(fiscal.getBasica().getIdBasica(), basica)) {
            throw new SecurityException("No puede desasignar un fiscal general de otra básica");
        }
        fiscal.setEstablecimientoAsignado(null);
        repo.save(fiscal);
    }
}
