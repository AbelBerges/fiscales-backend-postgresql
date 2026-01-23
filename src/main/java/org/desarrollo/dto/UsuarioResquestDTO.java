package org.desarrollo.dto;


import java.util.Date;

public record UsuarioResquestDTO(
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
