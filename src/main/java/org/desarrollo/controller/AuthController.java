package org.desarrollo.controller;

import org.desarrollo.config.ContextoUsuario;
import org.desarrollo.config.JwtService;
import org.desarrollo.dto.LoginRequest;
import org.desarrollo.dto.LoginResponse;
import org.desarrollo.model.Usuario;
import org.desarrollo.service.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manejador;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
        Authentication auth = manejador.authenticate(
                new UsernamePasswordAuthenticationToken(dto.usuario(), dto.password())
        );
        UsuarioDetails detalles = (UsuarioDetails) auth.getPrincipal();
        Usuario usuario = detalles.getUsuarioReal();
        String token = jwtService.generarToken(
                usuario.getIdUsuario(),
                usuario.getBasica() != null ? usuario.getBasica().getIdBasica() : null,
                usuario.getRol().name()
        );

        return ResponseEntity.ok(
                new LoginResponse(token, usuario.getRol().name(),
                        usuario.getBasica() != null ? usuario.getBasica().getIdBasica() : null)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> loguot() {
        return ResponseEntity.ok().build();
    }
}
