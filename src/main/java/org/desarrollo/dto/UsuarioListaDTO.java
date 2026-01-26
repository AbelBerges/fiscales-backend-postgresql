package org.desarrollo.dto;

public record UsuarioListaDTO(
        Integer idUsuario,
        String nomUser,
        String nombreUsuario,
        String apellidoUsuario,
        String clave,
        Integer edad,
        String correo,
        String telefono,
        Boolean activo,
        String rol,
        Integer idBasica
) {
}
