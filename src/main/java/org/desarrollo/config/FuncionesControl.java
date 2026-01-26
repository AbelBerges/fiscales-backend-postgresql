package org.desarrollo.config;

import org.desarrollo.dto.UsuarioResquestDTO;
import org.desarrollo.enumeradores.Rol;
import org.desarrollo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FuncionesControl {
    @Autowired
    private PasswordEncoder codificador;

    public void validarBasicaSegunRol(UsuarioResquestDTO dto, Rol rol) {
        if (rol != Rol.ADMIN_GLOBAL && dto.idBasica() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La básica es obligatoria para este rol");
        }

        if (rol == Rol.ADMIN_GLOBAL && dto.idBasica() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un administrador no debe tener una básica asignada");
        }
    }

    public Usuario encritarClave(Usuario u) {
        String hash = codificador.encode(u.getClave());
        u.setClave(hash);
        return u;
    }
}
