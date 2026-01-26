package org.desarrollo.mapper;

import org.desarrollo.dto.UsuarioActualizarDTO;
import org.desarrollo.dto.UsuarioResponseDTO;
import org.desarrollo.dto.UsuarioResquestDTO;
import org.desarrollo.enumeradores.Rol;
import org.desarrollo.model.Usuario;
import org.desarrollo.service.BasicaServicio;

import java.util.Date;
import java.util.Objects;

public class UsuarioMapper {

    private static BasicaServicio basicaServicio = new BasicaServicio();
    public static Usuario aEntidadDeCreacion(UsuarioResquestDTO dto) {
        Usuario nuevo = new Usuario();
        nuevo.setNomUser(dto.nomUser());
        nuevo.setNombreUsuario(dto.nombreUsuario());
        nuevo.setApellidoUsuario(dto.apellidoUsuario());
        nuevo.setClave(dto.clave());
        nuevo.setEdad(dto.edad());
        nuevo.setCorreo(dto.correo());
        nuevo.setTelefono(dto.telefono());
        nuevo.setActivo(dto.activo());
        return nuevo;
    }

    public static UsuarioResponseDTO aEntidadUsuarioResponse(Usuario userDto) {
        Integer eliId = null;
        String nomBasica = null;
        if (userDto.getBasica() != null) {
            eliId = userDto.getBasica().getIdBasica();
            nomBasica = userDto.getBasica().getNombre();
        }
        return new UsuarioResponseDTO(
                userDto.getIdUsuario(),
                userDto.getNomUser(),
                userDto.getNombreUsuario(),
                userDto.getApellidoUsuario(),
                userDto.getEdad(),
                userDto.getCorreo(),
                userDto.getTelefono(),
                userDto.isActivo(),
                //userDto.getFechaAcceso(),
                userDto.getRol().name(),
                eliId,
                nomBasica
        );
    }

    public static void aplicarActualizarUsuario(UsuarioActualizarDTO dto, Usuario u) {
        if (dto.nombreUsuario() != null) u.setNombreUsuario(dto.nombreUsuario());
        if (dto.apellidoUsuario() != null) u.setApellidoUsuario(dto.apellidoUsuario());
        if (dto.edad() != null) u.setEdad(dto.edad());
        if (dto.correo() != null) u.setCorreo(dto.correo());
        if (dto.telefono() != null) u.setTelefono(dto.telefono());
        if (dto.activo() != null) u.setActivo(dto.activo());
    }
}
