package org.desarrollo.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.weaver.patterns.IToken;
import org.desarrollo.enumeradores.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                Claims claims = jwtService.validarToken(token);
                Integer idUsuario = Integer.valueOf(claims.getSubject());
                String rol = (String) claims.get("rol");
                Integer idBasica = claims.get("basica", Integer.class);

                ContextoUsuario.set(new ContextoUsuario.UsuarioActual(idUsuario, Rol.valueOf(rol),idBasica));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                idUsuario,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + rol))
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("AUTH = " +
                        SecurityContextHolder.getContext().getAuthentication());
            }

            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
            ContextoUsuario.clear();
        }
    }
}
