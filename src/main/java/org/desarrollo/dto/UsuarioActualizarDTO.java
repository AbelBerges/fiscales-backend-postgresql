package org.desarrollo.dto;

public record UsuarioActualizarDTO(
        String nombreUsuario,
        String apellidoUsuario,
        Integer edad,
        String correo,
        String telefono,
        Boolean activo
) {
}
