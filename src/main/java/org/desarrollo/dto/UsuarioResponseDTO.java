package org.desarrollo.dto;

import java.util.Date;

public record UsuarioResponseDTO(
        Integer idUsuario,
        String nomUser,
        String nombreUsuario,
        String apellidoUsuario,
        Integer edad,
        String correo,
        String telefono,
        Boolean activo,
        //Date fechaAcceso,
        String rol,
        Integer idBasica,
        String nombreBasica
) {
}
