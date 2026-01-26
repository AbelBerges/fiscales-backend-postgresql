package org.desarrollo.controller;

import org.desarrollo.config.ContextoUsuario;
import org.desarrollo.config.FuncionesControl;
import org.desarrollo.dto.*;
import org.desarrollo.enumeradores.Rol;
import org.desarrollo.mapper.UsuarioMapper;
import org.desarrollo.model.Basica;
import org.desarrollo.model.Usuario;
import org.desarrollo.repository.UsuarioRepository;
import org.desarrollo.service.BasicaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;
    private PasswordEncoder codificador;
    private final BasicaServicio basicaServicio;
    private final FuncionesControl control;
    public UsuarioController(UsuarioRepository repo, PasswordEncoder codificador, BasicaServicio basicaServicio, FuncionesControl control) {
        this.repo = repo;
        this.codificador = codificador;
        this.basicaServicio = basicaServicio;
        this.control = control;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        if (ContextoUsuario.esAdminGlobal()) {
            List<UsuarioResponseDTO> lista = repo.findByActivoTrueOrderByApellidoUsuarioAsc().stream()
                    .map(UsuarioMapper::aEntidadUsuarioResponse)
                    .toList();
            return ResponseEntity.ok(lista);
        }
        if (ContextoUsuario.getUsuarioActual().rol() == Rol.COORDINADOR_BASICA) {
            return ResponseEntity.ok(repo.findByBasica_IdBasica(ContextoUsuario.getBasica()).stream()
                    .map(UsuarioMapper::aEntidadUsuarioResponse)
                    .toList()
            );
        }

        Usuario u = repo.findById(Objects.requireNonNull(ContextoUsuario.getBasica())).orElseThrow();

        return ResponseEntity.ok(
                List.of(UsuarioMapper.aEntidadUsuarioResponse(u))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable int id) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findById(id).map(UsuarioMapper::aEntidadUsuarioResponse)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        if (ContextoUsuario.getUsuarioActual().rol() == Rol.COORDINADOR_BASICA) {
            return repo.findById(id).map(existe -> {
                if (!Objects.equals(ContextoUsuario.getBasica(), existe.getBasica().getIdBasica())) {
                    throw new SecurityException("No puede buscar usuarios de otra básica");
                }
                return UsuarioMapper.aEntidadUsuarioResponse(existe);
            }).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @GetMapping("/buscar/{apellido}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorApellido(@PathVariable String apellido) {
        List<UsuarioResponseDTO> listar = repo.findByApellidoUsuarioContainingIgnoreCase(apellido).stream()
                .map(UsuarioMapper::aEntidadUsuarioResponse)
                .toList();
        return ResponseEntity.ok(listar);
    }

    @GetMapping("/buscarPorUser/{nomUser}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorNomUser(@PathVariable String nomUser) {
        List<UsuarioResponseDTO> listarUser = repo.findByNomUserContainingIgnoreCase(nomUser)
                .stream()
                .map(UsuarioMapper::aEntidadUsuarioResponse)
                .toList();
        return ResponseEntity.ok(listarUser);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> guardarUsuario(@RequestBody UsuarioResquestDTO dto) {
        if (dto.rol() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol es obligatorio");
        }
        Rol nuevoRol = Rol.valueOf(dto.rol());
        if (ContextoUsuario.esAdminGlobal()) {
            control.validarBasicaSegunRol(dto, nuevoRol);
            Usuario nuevo = UsuarioMapper.aEntidadDeCreacion(dto);
            nuevo.setRol(nuevoRol);
            if (nuevoRol != Rol.ADMIN_GLOBAL) {
                Integer tomoElId = dto.idBasica();
                Basica basica = basicaServicio.buscarPorId(tomoElId);
                nuevo.setBasica(basica);
            }
            control.encritarClave(nuevo);
            Usuario guardado = repo.save(nuevo);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(guardado));
        }
        if (ContextoUsuario.getUsuarioActual().rol() == Rol.COORDINADOR_BASICA) {
            if (!Objects.equals(ContextoUsuario.getBasica(), dto.idBasica())) {
                throw new SecurityException("No puede agregar el usuario a otra básica");
            }
            if (nuevoRol == Rol.ADMIN_GLOBAL) {
                throw new SecurityException("No tiene permisos para crear administradores");
            }
            Usuario nuevo = UsuarioMapper.aEntidadDeCreacion(dto);
            nuevo.setRol(nuevoRol);
            nuevo.setBasica(basicaServicio.buscarPorId(dto.idBasica()));
            control.encritarClave(nuevo);
            Usuario guardo = repo.save(nuevo);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(guardo));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioActualizarDTO dto) {
        if (dto.rol() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol es obligatorio");
        }
        Rol nuevoRol;
        try {
            nuevoRol = Rol.valueOf(dto.rol());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol es obligatorio");
        }
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findById(id).map(existe -> {
                existe.setRol(nuevoRol);
                if (nuevoRol != Rol.ADMIN_GLOBAL) {
                    existe.setBasica(basicaServicio.buscarPorId(dto.idBasica()));
                } else {
                    existe.setBasica(null);
                }
                UsuarioMapper.aplicarActualizarUsuario(dto, existe);
                Usuario actualizado = repo.save(existe);
                return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizado));
            }).orElse(ResponseEntity.notFound().build());
        }
        if (ContextoUsuario.getUsuarioActual().rol() == Rol.COORDINADOR_BASICA) {
            return repo.findById(id).map(existe -> {
                if (nuevoRol == Rol.ADMIN_GLOBAL) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tiene permiso para actualizar administradores");
                }
                existe.setRol(nuevoRol);
                if (!Objects.equals(ContextoUsuario.getBasica(), dto.idBasica())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puede actualizar el usuario de otra básica");
                }
                Basica nueva = basicaServicio.buscarPorId(dto.idBasica());
                existe.setBasica(nueva);
                UsuarioMapper.aplicarActualizarUsuario(dto, existe);
                Usuario actualizado = repo.save(existe);
                return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizado));
            }).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/clave/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarClaveUsuario(@PathVariable Integer id, @RequestBody UsuarioActualizarClaveDTO claveDto) {
        return repo.findById(id).map(existe -> {
            String hash = codificador.encode(claveDto.clave());
            existe.setClave(hash);
            Usuario actualizar = repo.save(existe);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizar));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/username/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarNomUserUsuario(@PathVariable Integer id, @RequestBody UsuarioActualizarNomUserDTO nomUser) {
        System.out.println("Ver lo que viene " + id + " - " + nomUser);
        return repo.findById(id).map(existe -> {
            existe.setNomUser(nomUser.nomUser());
            Usuario actualizo = repo.save(existe);
            return ResponseEntity.ok(UsuarioMapper.aEntidadUsuarioResponse(actualizo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/baja/{id}")
    public ResponseEntity<Boolean> bajaUsuario(@PathVariable Integer id)  throws AccessDeniedException{
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findById(id).map(existe -> {
                existe.setActivo(false);
                repo.save(existe);
                return ResponseEntity.ok(true);
            }).orElse(ResponseEntity.notFound().build());
        }
        if (ContextoUsuario.getUsuarioActual().rol() == Rol.COORDINADOR_BASICA) {
            return repo.findById(id).map(existe -> {
                if (!Objects.equals(ContextoUsuario.getUsuarioActual().rol(), existe.getRol())) {
                    throw new SecurityException("No puede dar de baja un usuario de otra básica");
                }
                existe.setActivo(false);
                repo.save(existe);
                return ResponseEntity.ok(true);
            }).orElse(ResponseEntity.notFound().build());
        }
        throw new AccessDeniedException("No autorizado");
    }

}
